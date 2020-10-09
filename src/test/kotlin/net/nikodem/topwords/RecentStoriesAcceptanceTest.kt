package net.nikodem.topwords

import EXPECTED_TOP_WORDS_OF_RECENT_STORIES
import MOCKED_RECENT_STORY_IDS
import com.nhaarman.mockitokotlin2.whenever
import net.nikodem.topwords.hackerNewsApi.RecentStoriesIdsApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
class RecentStoriesAcceptanceTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var recentStoriesIdsApi: RecentStoriesIdsApi

    @Test()
    fun `Returns top words from 250 most recent HN stories in descending order`() {
        whenever(recentStoriesIdsApi.fetchRecentStoriesIds()).thenReturn(MOCKED_RECENT_STORY_IDS)
        mvc.get("/v1/topwords/recentstories")
                .andExpect {
                    status { isOk }
                    content {
                        json(EXPECTED_TOP_WORDS_OF_RECENT_STORIES)
                    }
                }
    }
}

