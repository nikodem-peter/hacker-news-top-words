package net.nikodem.topwords

data class TopWord(
        val word: String,
        val occurrences: Int
)

data class TopWordsResponse(val topWords: List<TopWord>)


data class HnItem(
        val id: Long,
        val type: String,
        val title: String?,
        val text: String?
)