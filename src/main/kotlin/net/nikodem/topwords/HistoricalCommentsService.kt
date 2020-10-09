package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.ItemDetailApi
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import org.apache.commons.text.StringEscapeUtils
import org.springframework.stereotype.Service

@Service
class HistoricalCommentsService(
        private val itemIdsApi: ItemIdsApi,
        private val itemDetailApi: ItemDetailApi
) {

    fun findTopWords(): List<TopWord> {
        val candidateIds = getHistoricalCommentItemCandidates()
        val fetchedItems = candidateIds.map { fetchItem(it) }
        val cleanedWords = fetchedItems
                .filter { it.type == "comment" }
                .map { it.text ?: "" }
                .map { StringEscapeUtils.unescapeHtml4(it) }
                .flatMap { splitToNormalizedWords(it) }
                .filter { notStopWord(it) }
        return rankByOccurrences(cleanedWords)
                .take(25)

    }

    fun rankByOccurrences(cleanedWords: List<String>): List<TopWord> {
        return cleanedWords.groupingBy { it }.eachCount()
                .entries
                .map { TopWord(word = it.key, occurrences = it.value) }
                .sortedByDescending { it.word.length }
                .sortedByDescending { it.occurrences }
    }


    //todo explore whether it is worth it to also do this for job stories, askhn and showhn
    private fun eliminateStories(candidateIds: List<Long>): List<Long> {
        val recentStoryIds = itemIdsApi.fetchRecentStoryIds().toSet()
        return candidateIds.filter { !recentStoryIds.contains(it) }
    }

    private fun getHistoricalCommentItemCandidates(): List<Long> {
        val maxId = itemIdsApi.fetchMaxItemId()
        return (maxId downTo (maxId - 1000)).toList()
    }

    private fun fetchItem(itemId: Long) = itemDetailApi.fetchItemDetail(itemId)
}