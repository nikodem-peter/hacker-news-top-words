package net.nikodem.topwords.hackerNewsApi

import net.nikodem.topwords.HnUser
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UserApi(private val restTemplate: RestTemplate) {

    fun fetchUserDetail(userId: String): HnUser {
        return restTemplate.getForObject(
                "https://hacker-news.firebaseio.com/v0/user/${userId}.json",
                HnUser::class.java
        ) ?: throw Exception()
    }

}
