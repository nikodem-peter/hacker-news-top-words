package net.nikodem.topwords

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
class HistoricalCommentsServiceTest {

    @Autowired
    lateinit var historicalCommentsService: HistoricalCommentsService

    @Test
    fun testFind() {
        val result = historicalCommentsService.findTopWords()
        println(result)
    }
}