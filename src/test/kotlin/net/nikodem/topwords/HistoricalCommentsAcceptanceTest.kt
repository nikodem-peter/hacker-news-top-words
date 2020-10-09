package net.nikodem.topwords

import EXPECTED_TOP_WORDS_OF_HISTORICAL_COMMENTS
import MOCKED_MAX_ITEM_ID
import com.nhaarman.mockitokotlin2.whenever
import net.nikodem.topwords.hackerNewsApi.ItemIdsApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class HistoricalCommentsAcceptanceTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var idsApi: ItemIdsApi

    @Test()
    fun `Returns top 25 words from historical comments among 1000 most recent HN posts in descending order`() {
        whenever(idsApi.fetchMaxItemId()).thenReturn(MOCKED_MAX_ITEM_ID)

        mvc.get("/v1/topwords/historicalcomments")
                .andExpect {
                    status { isOk }
                    content {
                        json(EXPECTED_TOP_WORDS_OF_HISTORICAL_COMMENTS)
                    }
                }
    }
}