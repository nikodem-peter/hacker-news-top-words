package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.ItemDetailApi
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import net.nikodem.topwords.hackerNewsApi.UserApi
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.stream.Collectors

@Service
class KarmaService(
        private val itemIdsApi: ItemIdsApi,
        private val itemDetailApi: ItemDetailApi,
        private val userApi: UserApi
) {

    fun findTopKarmaWords(): List<WordWithKarma> {
        val itemIds = getItemCandidates()
        val fetchedComments = concurrentlyFetchComments(itemIds)
        val commentsWithKarma = concurrentlyAddKarma(fetchedComments)

        val cleanedWords = commentsWithKarma
                .flatMap { findTopWordsWithKarma(it) }
        return rankByKarma(cleanedWords)
                .take(10)
    }

    private fun findTopWordsWithKarma(comment: CommentWithKarma): List<WordWithKarma> {
        val cleanCommentsWords = splitToNormalizedWords(comment.text)
                .filter { notStopWord(it) }
        return rankByOccurrences(cleanCommentsWords)
                .take(10)
                .map { WordWithKarma(it.word, comment.karma) }
    }

    fun rankByKarma(cleanedWords: List<WordWithKarma>): List<WordWithKarma> {
        return cleanedWords.groupBy { it.word }
                .map { WordWithKarma(word = it.key, karma = it.value.map { it.karma }.sum()) }
                .sortedByDescending { it.word.length }
                .sortedByDescending { it.karma }
    }

    private fun concurrentlyAddKarma(fetchedComments: List<KarmaService.Comment>): List<CommentWithKarma> {
        val usersKarma: Map<String, Long> = fetchedComments.map { it.userId }.distinct()
                .filter { it.isNotBlank() }
                .parallelStream()
                .map { userApi.fetchUserDetail(it) }
                .collect(Collectors.toList())
                .map { it.id to it.karma }.toMap()

        return fetchedComments.map { CommentWithKarma(it.text, usersKarma.getOrDefault(it.userId, 0)) }
    }

    private fun getItemCandidates(): List<Long> {
        val maxId = itemIdsApi.fetchMaxItemId()
        return (maxId downTo (maxId - 200)).toList()
    }

    private fun concurrentlyFetchComments(storyIds: List<Long>): List<Comment> {
        return storyIds
                .map { BigInteger.valueOf(it) } //avoiding incompatibility between Java's and Kotlin's Long primitive types
                .parallelStream()
                .map { fetchComment(it) }
                .collect(Collectors.toList())
                .filter { it.valid }
                .take(100)
    }

    private fun fetchComment(itemId: BigInteger): Comment {
        val id = itemId.toLong()
        val item = itemDetailApi.fetchItemDetail(id)
        return if (item.type == "comment") {
            Comment(
                    text = item.text?.let { StringEscapeUtils.unescapeHtml4(it) } ?: "",
                    userId = item.by ?: "",
                    valid = true
            )
        } else {
            Comment(text = "", userId = "", valid = false)
        }
    }

    class Comment(val text: String, val userId: String, val valid: Boolean)
}