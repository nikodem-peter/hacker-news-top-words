package net.nikodem.topwords.hackerNewsApi

import net.nikodem.topwords.HnItem
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ItemDetailApi(private val restTemplate: RestTemplate) {

    fun fetchItemDetail(itemId: Long): HnItem {
        val item = restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/item/${itemId}.json",
                HnItem::class.java
        )
        println(item)
        return item ?: throw Exception()
    }
}
