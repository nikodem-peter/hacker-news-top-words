package net.nikodem.topwords

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class RecentStoriesAcceptanceTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `Returns top words from 250 most recent HN stories in descending order`() {
        mvc.get("/v1/topwords/recentstories")
                .andExpect {
                    status { isOk }
                    content { json(EXPECTED_TOP_WORDS) }
                }
    }

}

const val EXPECTED_TOP_WORDS = """
    {
        "topWords":[
            {
             "word": "GraphQL",
             "occurrences": 25
            },
            {
             "word": "GraphQL",
             "occurrences": 25
            },
            {
             "word": "GraphQL",
             "occurrences": 25
            },
            {
             "word": "GraphQL",
             "occurrences": 25
            },
            {
             "word": "GraphQL",
             "occurrences": 25
            }
        ]
    }
"""