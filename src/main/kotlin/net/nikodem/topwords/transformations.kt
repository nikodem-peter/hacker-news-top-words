package net.nikodem.topwords

import net.nikodem.topwords.stopwords.STOP_WORDS


fun splitToNormalizedWords(text: String): List<String> = text
        .toLowerCase()
        .split(
                ".",
                ",",
                "?",
                "!",
                ";",
                ":",
                "–",
                "-",
                "<",
                ">",
                "=",
                "/",
                "\\",
                "\t",
                "\n",
                " ")
        .filter { it.isNotBlank() }

fun notStopWord(it: String) = !STOP_WORDS.contains(it)

fun rankByOccurrences(cleanedWords: List<String>): List<TopWord> {
    return cleanedWords.groupingBy { it }.eachCount()
            .entries
            .map { TopWord(word = it.key, occurrences = it.value) }
            .sortedByDescending { it.word.length }
            .sortedByDescending { it.occurrences }
}