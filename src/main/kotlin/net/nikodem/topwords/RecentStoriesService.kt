package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.ItemDetailApi
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import org.springframework.stereotype.Service

@Service
class RecentStoriesService(private val itemIdsApi: ItemIdsApi, private val itemDetailApi: ItemDetailApi) {

    fun findTopWords(): List<TopWord> {
        val storyIds = fetchRecentStoriesIds()
                .take(250)
        val fetchedItems = storyIds
                .map { fetchTitle(it) }
        val cleanWords = fetchedItems
                .flatMap { splitToNormalizedWords(it) }
                .filter { notStopWord(it) }
        return rankByOccurrences(cleanWords)
                .take(25)
    }

    fun fetchRecentStoriesIds(): List<Long> = itemIdsApi.fetchRecentStoryIds()

    fun fetchTitle(itemId: Long): String = itemDetailApi.fetchItemDetail(itemId).title ?: throw Exception()
}