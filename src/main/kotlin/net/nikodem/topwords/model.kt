package net.nikodem.topwords

data class TopWord(
        val word: String,
        val occurrences: Int
)

data class WordWithKarma(val word: String, val karma: Long)

data class TopWordsResponse(val topWords: List<TopWord>)
data class WordsWithKarmaResponse(val topWords: List<WordWithKarma>)

data class CommentWithKarma(val text: String, val karma: Long)


data class HnItem(
        val id: Long,
        val type: String,
        val title: String?,
        val text: String?,
        val by: String?
)

class HnUser(
        val id: String,
        val karma: Long
)