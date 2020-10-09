package net.nikodem.topwords

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TopWordsController(
        private val recentStoriesService: RecentStoriesService,
        private val historicalCommentsService: HistoricalCommentsService,
        private val karmaService: KarmaService
) {

    @GetMapping("/v1/topwords/recentstories")
    fun getTopWordsFromRecentStories(): TopWordsResponse {
        return TopWordsResponse(topWords = recentStoriesService.findTopWords())
    }

    @GetMapping("/v1/topwords/historicalcomments")
    fun getTopWordsFromHistoricalComments(): TopWordsResponse {
        return TopWordsResponse(topWords = historicalCommentsService.findTopWords())
    }
    @GetMapping("/v1/topwords/karma")
    fun getTopKarmaWordsFromRecentComments(): WordsWithKarmaResponse {
        return WordsWithKarmaResponse(topWords = karmaService.findTopKarmaWords())
    }



}