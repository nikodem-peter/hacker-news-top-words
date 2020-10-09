package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.ItemDetailApi
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.stream.Collectors

@Service
class RecentStoriesService(private val itemIdsApi: ItemIdsApi, private val itemDetailApi: ItemDetailApi) {

    fun findTopWords(): List<TopWord> {
        val storyIds = itemIdsApi.fetchRecentStoryIds()
                .take(250)

        val fetchedTitles = concurrentlyFetchTitles(storyIds)

        val cleanWords = fetchedTitles
                .flatMap { splitToNormalizedWords(it) }
                .filter { notStopWord(it) }
        return rankByOccurrences(cleanWords)
                .take(25)
    }

    private fun concurrentlyFetchTitles(storyIds: List<Long>): List<String> {
        return storyIds
                .map { BigInteger.valueOf(it) } //avoiding incompatibility between Java's and Kotlin's Long primitive types
                .parallelStream()
                .map { fetchTitle(it) }
                .collect(Collectors.toList())
    }

    private fun fetchTitle(itemId: BigInteger): String {
        val id = itemId.toLong();
        return itemDetailApi.fetchItemDetail(id).title ?: throw Exception()
    }
}