package net.nikodem.topwords

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecentStoriesServiceTest {

    @Autowired
    lateinit var recentStoriesService: RecentStoriesService

    @Test
    fun testFind() {
        val result = recentStoriesService.findTopWords()
        println(result)
    }
}
