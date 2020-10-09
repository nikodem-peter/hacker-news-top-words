package net.nikodem.topwords.hackerNewsApi

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ItemIdsApi(private val restTemplate: RestTemplate) {

    fun fetchRecentStoryIds(): List<Long> {
        val ids = restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/newstories.json",
                List::class.java
        ) as List<Long>
        println(ids)
        return ids
    }

    fun fetchMaxItemId(): Long {
        val maxId = restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/maxitem.json",
                Long::class.java
        ) ?: throw Exception()
        println(maxId)
        return maxId
    }
}