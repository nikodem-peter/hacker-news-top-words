package net.nikodem.topwords

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TopWordsController(
        private val recentStoriesService: RecentStoriesService,
        private val historicalCommentsService: HistoricalCommentsService
) {

    @GetMapping("/v1/topwords/recentstories")
    fun getTopWordsFromRecentStories(): TopWordsResponse {
        return TopWordsResponse(topWords = recentStoriesService.findTopWords())
    }


}