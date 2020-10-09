package net.nikodem.topwords.hackerNewsApi

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RecentStoriesIdsApi(private val restTemplate: RestTemplate) {

    fun fetchRecentStoriesIds(): List<Long> {
        val ids = restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/newstories.json",
                List::class.java
        ) as List<Long>
        println(ids)
        return ids
    }
}