package com.github.benpollarduk.ktaf.extensions

import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.utilities.NEWLINE

/**
 * Converts the [String] to a [Description].
 */
public fun String.toDescription(): com.github.benpollarduk.ktaf.assets.Description {
    return com.github.benpollarduk.ktaf.assets.Description(this)
}

/**
 * Converts the [String] to an [Identifier].
 */
public fun String.toIdentifier(): Identifier {
    return Identifier(this)
}

/**
 * Checks if the [String] is equal to the given [identifier] and returns a [Boolean] where the value is true if the
 * [String] and the [identifier] are equal, else false.
 */
public fun String.equalsIdentifier(identifier: Identifier): Boolean {
    return identifier.equals(this)
}

/**
 * Checks if the [String] is equal to the given [examinable] and returns a [Boolean] where the value is true if the
 * [String] and the [examinable] are equal, else false.
 */
public fun String.equalsExaminable(examinable: com.github.benpollarduk.ktaf.assets.Examinable?): Boolean {
    if (examinable == null) {
        return false
    }
    return examinable.identifier.equals(this)
}

/**
 * Returns a version of the [String] that ends with either ?, ! or .
 */
public fun String.ensureFinishedSentence(): String {
    if (this.isEmpty()) {
        return this
    }

    if (this.endsWith('.') || this.endsWith('!') || this.endsWith('?')) {
        return this
    }

    if (this.endsWith(',')) {
        return this.substring(0, this.length - 1) + '.'
    }

    return "$this."
}

/**
 * Returns a version of the [String] that is not finished with either a ?, ! or .
 */
public fun String.removeSentenceEnd(): String {
    if (this.isEmpty()) {
        return this
    }

    if (this.endsWith('.') || this.endsWith('!') || this.endsWith('?')) {
        return this.substring(0, this.length - 1)
    }

    return this
}

/**
 * Returns a version of the [String] encapsulated by "".
 */
public fun String.toSpeech(): String {
    var working = this

    if (working.isEmpty()) {
        return "\"\""
    }

    if (!working.startsWith("\"")) {
        working = "\"" + working
    }

    if (!working.endsWith("\"")) {
        working += "\""
    }

    return working
}

/**
 * Determines if a word is plural.
 */
public fun String.isPlural(): Boolean {
    if (this.isEmpty()) {
        return false
    }

    val space = ' '
    val lastWord = this.trimEnd(space).split(space).last()
    return lastWord.endsWith("S", true)
}

/**
 * Get if this is a vowel.
 */
public fun String.isVowel(): Boolean {
    if (this.length != 1) {
        return false
    }

    val vowels = listOf("A", "E", "I", "O", "U")
    return vowels.any { it.equals(this, true) }
}

/**
 * Get an objectified for a word.
 */
public fun String.getObjectifier(): String {
    if (this.isEmpty()) {
        return ""
    }

    val space = ' '
    val firstWord = this.trimStart(space).split(space).first()
    val lastWord = this.trimEnd(space).split(space).last()

    if (lastWord.isPlural()) {
        return "some"
    }
    if ((firstWord[0].toString().isVowel()) && !firstWord.startsWith("U", ignoreCase = true)) {
        return "an"
    }

    return "a"
}

/**
 * Ensure that the [String] starts with a lower case letter.
 */
public fun String.startWithLower(): String {
    if (this.isEmpty()) {
        return this
    }

    if (this.length == 1) {
        return this.lowercase()
    }

    return "${this.substring(0, 1).lowercase()}${this.substring(1)}"
}

/**
 * Convert to sentence case.
 */
public fun String.toSentenceCase(): String {
    if (this.isEmpty()) {
        return this
    }

    if (this.length == 1) {
        return this.uppercase()
    }

    val first = this.substring(0, 1).uppercase()
    val rest = this.substring(1)
    return "$first$rest"
}

/**
 * Compare this string to [other] with no case sensitivity.
 */
public fun String.insensitiveEquals(other: String): Boolean {
    return this.equals(other, true)
}

/**
 * Try and parse this string to an [Int]. If the parse succeeds then an [Int] is returned, else null.
 */
public fun String.tryParseInt(): Int? {
    return try {
        this.toInt()
    } catch (e: NumberFormatException) {
        null
    }
}

/**
 * Add a [sentence] to this [String].
 */
public fun String.addSentence(sentence: String): String {
    if (this.isEmpty()) {
        return sentence
    }

    if (sentence.isEmpty()) {
        return this
    }

    return "$this $sentence"
}

/**
 * Remove any lines of whitespace from this [String].
 */
public fun String.removeWhitespaceLines(): String {
    val newline = NEWLINE
    val lines = this.split(newline)
    var noPaddingLines = ""

    lines.forEach { line ->
        if (line.isNotEmpty() && line.any { it != ' ' }) {
            noPaddingLines += line + newline
        }
    }

    return noPaddingLines
}

/**
 * Preen all unwanted characters from this [String]. Fixes incorrect combinations of \n and \r so that if the string
 * ends with either \n or \r it will be fixed to only end with \n.
 */
internal fun String.preen(): String {
    var output = this.replace("\r\n", "\n")
    output = output.replace("\n\r", "\n")
    output = output.replace("\r", "\n")
    return output
}
