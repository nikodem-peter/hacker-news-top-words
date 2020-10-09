package net.nikodem.topwords

import net.nikodem.topwords.hackerNewsApi.RecentStoriesIdsApi
import net.nikodem.topwords.stopwords.STOP_WORDS
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Exception

@Service
class RecentStoriesService(private val restTemplate: RestTemplate, private val recentStoriesApi: RecentStoriesIdsApi) {

    fun findTopWords(): List<TopWord> {
        return fetchRecentStoriesIds()
                .take(250)
                .map { fetchTitle(it) }
                .flatMap { splitToNormalizedWords(it) }
                .filter { !STOP_WORDS.contains(it) }
                .groupingBy { it }.eachCount()
                .entries
                .map { TopWord(word = it.key, occurrences = it.value) }
                .sortedByDescending { it.word.length }
                .sortedByDescending { it.occurrences }
                .take(25)
    }

    fun splitToNormalizedWords(text: String): List<String> {
        return text.split(
                ".",
                ",",
                "?",
                "!",
                ";",
                ":",
                "–",
                "\t",
                "\n",
                " ")
                .filter { it.isNotBlank() }
                .map { it.toLowerCase() }
    }


    fun fetchRecentStoriesIds(): List<Long> {
        return recentStoriesApi.fetchRecentStoriesIds()
    }

    fun fetchTitle(itemId: Long): String {
        val item = restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/item/${itemId}.json",
                Item::class.java
        )
        println(item)
        return item?.title ?: throw Exception()
    }

    data class Item(
            val id: Long,
            val title: String
    )
}