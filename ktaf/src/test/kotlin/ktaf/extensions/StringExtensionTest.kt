package ktaf.extensions

import ktaf.assets.Identifier
import ktaf.assets.Item
import ktaf.helpers.newline
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StringExtensionTest {

    @Test
    fun `given an empty string when toDescription then not null`() {
        // Given
        val value = ""

        // When
        val result = value.toDescription()

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given an empty string when toIdentifier then return not null`() {
        // Given
        val value = ""

        // When
        val result = value.toIdentifier()

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given a string and an unequal identifier when calling equalsIdentifier then return false`() {
        // Given
        val value = "A"

        // When
        val result = value.equalsIdentifier(Identifier("B"))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a string and an equal identifier when calling equalsIdentifier then return true`() {
        // Given
        val value = "A"

        // When
        val result = value.equalsIdentifier(Identifier("A"))

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given a string and null when calling equalsExaminable then return false`() {
        // Given
        val value = "A"

        // When
        val result = value.equalsExaminable(null)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a string and an unequal examinable when calling equalsExaminable then return false`() {
        // Given
        val value = "A"

        // When
        val result = value.equalsExaminable(Item("B", ""))

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a string and an equal examinable when calling equalsExaminable then return true`() {
        // Given
        val value = "A"

        // When
        val result = value.equalsExaminable(Item("A", ""))

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given an empty string when ensureFinishedSentence then return empty string`() {
        // Given
        val value = ""

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("", result)
    }

    @Test
    fun `given A when ensureFinishedSentence then return A and a full stop`() {
        // Given
        val value = "A"

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("A.", result)
    }

    @Test
    fun `given A and a full stop when ensureFinishedSentence then return A and a full stop`() {
        // Given
        val value = "A."

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("A.", result)
    }

    @Test
    fun `given A and a question mark when ensureFinishedSentence then return A and a question mark`() {
        // Given
        val value = "A?"

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("A?", result)
    }

    @Test
    fun `given A and an exclamation mark when ensureFinishedSentence then return A and an exclamation mark`() {
        // Given
        val value = "A!"

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("A!", result)
    }

    @Test
    fun `given A and an comma when ensureFinishedSentence then return A and a full stop`() {
        // Given
        val value = "A,"

        // When
        val result = value.ensureFinishedSentence()

        // Then
        Assertions.assertEquals("A.", result)
    }

    @Test
    fun `given ABC when removeSentenceEnd then return ABC`() {
        // Given
        val value = "ABC"

        // When
        val result = value.removeSentenceEnd()

        // Then
        Assertions.assertEquals("ABC", result)
    }

    @Test
    fun `given ABC and a full stop when removeSentenceEnd then return ABC`() {
        // Given
        val value = "ABC."

        // When
        val result = value.removeSentenceEnd()

        // Then
        Assertions.assertEquals("ABC", result)
    }

    @Test
    fun `given ABC and a question mark when removeSentenceEnd then return ABC`() {
        // Given
        val value = "ABC?"

        // When
        val result = value.removeSentenceEnd()

        // Then
        Assertions.assertEquals("ABC", result)
    }

    @Test
    fun `given ABC and an exclamation mark when removeSentenceEnd then return ABC`() {
        // Given
        val value = "ABC!"

        // When
        val result = value.removeSentenceEnd()

        // Then
        Assertions.assertEquals("ABC", result)
    }

    @Test
    fun `given empty string when toSpeech then return speech mark speech mark`() {
        // Given
        val value = ""

        // When
        val result = value.toSpeech()

        // Then
        Assertions.assertEquals("\"\"", result)
    }

    @Test
    fun `given ABC when toSpeech then return speech mark speech mark ABC speech mark`() {
        // Given
        val value = "ABC"

        // When
        val result = value.toSpeech()

        // Then
        Assertions.assertEquals("\"ABC\"", result)
    }

    @Test
    fun `given speech mark ABC when toSpeech then return speech mark ABC speech mark`() {
        // Given
        val value = "\"ABC"

        // When
        val result = value.toSpeech()

        // Then
        Assertions.assertEquals("\"ABC\"", result)
    }

    @Test
    fun `given ABC speech mark when toSpeech then return speech mark ABC speech mark`() {
        // Given
        val value = "ABC\""

        // When
        val result = value.toSpeech()

        // Then
        Assertions.assertEquals("\"ABC\"", result)
    }

    @Test
    fun `given speech mark ABC speech mark when toSpeech then return speech mark ABC speech mark`() {
        // Given
        val value = "\"ABC\""

        // When
        val result = value.toSpeech()

        // Then
        Assertions.assertEquals("\"ABC\"", result)
    }

    @Test
    fun `given empty string when isPlural then return false`() {
        // Given
        val value = ""

        // When
        val result = value.isPlural()

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given abc when isPlural then return false`() {
        // Given
        val value = "abc"

        // When
        val result = value.isPlural()

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given lots of horses when isPlural then return true`() {
        // Given
        val value = "lots of horses"

        // When
        val result = value.isPlural()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given empty string when isVowel then return false`() {
        // Given
        val value = ""

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given test when isVowel then return false`() {
        // Given
        val value = "test"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given space when isVowel then return false`() {
        // Given
        val value = " "

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given a when isVowel then return true`() {
        // Given
        val value = "a"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given e when isVowel then return true`() {
        // Given
        val value = "e"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given i when isVowel then return true`() {
        // Given
        val value = "i"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given o when isVowel then return true`() {
        // Given
        val value = "o"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given u when isVowel then return true`() {
        // Given
        val value = "u"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given A when isVowel then return true`() {
        // Given
        val value = "A"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given E when isVowel then return true`() {
        // Given
        val value = "E"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given I when isVowel then return true`() {
        // Given
        val value = "I"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given O when isVowel then return true`() {
        // Given
        val value = "O"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given U when isVowel then return true`() {
        // Given
        val value = "U"

        // When
        val result = value.isVowel()

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given ABC when startWithLower then return aBC`() {
        // Given
        val value = "ABC"

        // When
        val result = value.startWithLower()

        // Then
        Assertions.assertEquals("aBC", result)
    }

    @Test
    fun `given abc when startWithLower then return abc`() {
        // Given
        val value = "abc"

        // When
        val result = value.startWithLower()

        // Then
        Assertions.assertEquals("abc", result)
    }

    @Test
    fun `given A when startWithLower then return a`() {
        // Given
        val value = "A"

        // When
        val result = value.startWithLower()

        // Then
        Assertions.assertEquals("a", result)
    }

    @Test
    fun `given empty string when startWithLower then return empty string`() {
        // Given
        val value = ""

        // When
        val result = value.startWithLower()

        // Then
        Assertions.assertEquals("", result)
    }

    @Test
    fun `given empty string when toSentenceCase then return empty string`() {
        // Given
        val value = ""

        // When
        val result = value.toSentenceCase()

        // Then
        Assertions.assertEquals("", result)
    }

    @Test
    fun `given the dog when toSentenceCase then return The dog`() {
        // Given
        val value = "the dog"

        // When
        val result = value.toSentenceCase()

        // Then
        Assertions.assertEquals("The dog", result)
    }

    @Test
    fun `given empty string when getObjectifier then return empty string`() {
        // Given
        val value = ""

        // When
        val result = value.toSentenceCase()

        // Then
        Assertions.assertEquals("", result)
    }

    @Test
    fun `given ball when getObjectifier then return a`() {
        // Given
        val value = "ball"

        // When
        val result = value.getObjectifier()

        // Then
        Assertions.assertEquals("a", result)
    }

    @Test
    fun `given eggs when getObjectifier then return some`() {
        // Given
        val value = "eggs"

        // When
        val result = value.getObjectifier()

        // Then
        Assertions.assertEquals("some", result)
    }

    @Test
    fun `given abc when insensitive compare to DEF then return false`() {
        // Given
        val value = "abc"

        // When
        val result = value.insensitiveEquals("DEF")

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given abc when insensitive compare to ABC then return true`() {
        // Given
        val value = "abc"

        // When
        val result = value.insensitiveEquals("ABC")

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given abc when try parse to int then return null`() {
        // Given
        val value = "abc"

        // When
        val result = value.tryParseInt()

        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `given 123 when try parse to int then return 123`() {
        // Given
        val value = "123"

        // When
        val result = value.tryParseInt()

        // Then
        Assertions.assertEquals(123, result)
    }

    @Test
    fun `given empty string when adding new sentence of abc then return abc`() {
        // Given
        val value = ""

        // When
        val result = value.addSentence("abc")

        // Then
        Assertions.assertEquals("abc", result)
    }

    @Test
    fun `given abc when adding new sentence of empty string then return abc`() {
        // Given
        val value = "abc"

        // When
        val result = value.addSentence("")

        // Then
        Assertions.assertEquals("abc", result)
    }

    @Test
    fun `given abc when adding new sentence of def then return abc def`() {
        // Given
        val value = "abc"

        // When
        val result = value.addSentence("def")

        // Then
        Assertions.assertEquals("abc def", result)
    }

    @Test
    fun `given abc when preening input then return abc`() {
        // Given
        val value = "abc"

        // When
        val result = value.preen()

        // Then
        Assertions.assertEquals("abc", result)
    }

    @Test
    fun `given CR LF when preening input then return LF`() {
        // Given
        val value = "\n\r"

        // When
        val result = value.preen()

        // Then
        Assertions.assertEquals("\n", result)
    }

    @Test
    fun `given LF CR when preening input then return LF`() {
        // Given
        val value = "\r\n"

        // When
        val result = value.preen()

        // Then
        Assertions.assertEquals("\n", result)
    }

    @Test
    fun `given LF when preening input then return LF`() {
        // Given
        val value = "\r"

        // When
        val result = value.preen()

        // Then
        Assertions.assertEquals("\n", result)
    }

    @Test
    fun `given 3 lines total 2 padding when removing whitespace lines then return 1 line`() {
        // Given
        val newline = newline()
        val value = "${newline}Test$newline$newline"

        // When
        val result = value.removeWhitespaceLines()

        // Then
        Assertions.assertEquals("Test$newline", result)
    }
}
