package net.nikodem.topwords

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Exception

@Service
class RecentStoriesService(private val restTemplate: RestTemplate) {

    fun findTopWords(): Any {
        val recentStoriesIds = fetchRecentStoriesIds()
                .take(3)
                .map { fetchTitle(it) }
                .flatMap { splitToNormalizedWords(it) }

        return recentStoriesIds
    }

    fun splitToNormalizedWords(text: String): List<String> {
        return text.split(
                ".",
                ",",
                "?",
                "!",
                ";",
                ":",
                "\t",
                "\n",
                " ")
                .filter { it.isNotBlank() }
                .map { it.toLowerCase() }
    }


    fun fetchRecentStoriesIds(): List<Long> {
        return (restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/newstories.json",
                List::class.java
        ) as List<Long>)
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