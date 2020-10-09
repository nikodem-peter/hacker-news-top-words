package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.ItemDetailApi
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.stream.Collectors

@Service
class HistoricalCommentsService(
        private val itemIdsApi: ItemIdsApi,
        private val itemDetailApi: ItemDetailApi
) {

    fun findTopWords(): List<TopWord> {
        val itemIds = getHistoricalCommentItemCandidates()
        val fetchedComments = concurrentlyFetchCommentTexts(itemIds)
        val cleanedWords = fetchedComments
                .flatMap { splitToNormalizedWords(it) }
                .filter { notStopWord(it) }
        return rankByOccurrences(cleanedWords)
                .take(25)

    }

    private fun getHistoricalCommentItemCandidates(): List<Long> {
        val maxId = itemIdsApi.fetchMaxItemId()
        return (maxId downTo (maxId - 1000)).toList()
    }

    private fun concurrentlyFetchCommentTexts(storyIds: List<Long>): List<String> {
        return storyIds
                .map { BigInteger.valueOf(it) } //avoiding incompatibility between Java's and Kotlin's Long primitive types
                .parallelStream()
                .map { fetchCommentText(it) }
                .collect(Collectors.toList())
    }

    private fun fetchCommentText(itemId: BigInteger): String {
        val id = itemId.toLong()
        val item = itemDetailApi.fetchItemDetail(id)
        return if (item.type == "comment") {
            item.text?.let { StringEscapeUtils.unescapeHtml4(it) } ?: ""
        } else {
            ""
        }
    }
}