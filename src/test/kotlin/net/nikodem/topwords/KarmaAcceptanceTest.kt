package net.nikodem.topwords

import EXPECTED_TOP_KARMA_WORDS
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
class KarmaAcceptanceTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var idsApi: ItemIdsApi

    @Test()
    fun `Returns top 10 words from recent HN comments among by commentors with greatest Karma in descending order`() {
        whenever(idsApi.fetchMaxItemId()).thenReturn(MOCKED_MAX_ITEM_ID)

        mvc.get("/v1/topwords/karma")
                .andExpect {
                    status { isOk }
                    content { json(EXPECTED_TOP_KARMA_WORDS) }
                }

        // I was not able to figure out how to mock user's karma efficiently so the test is completely unstable
    }
}