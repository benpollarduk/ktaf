package com.github.benpollarduk.ktaf.logic.discovery

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream

@Suppress("MaxLineLength")
class GameCatalogResolverTest {
    @Test
    fun `given example jar containing one game template object when resolving catalog entries for jar file then one entry returned`() {
        // Given
        val testJarStream = javaClass.classLoader.getResourceAsStream("test.jar")
        val tempFile = File.createTempFile("test-jar", ".jar")
        tempFile.deleteOnExit()

        // write the contents of the resource to the temporary file
        testJarStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }

        // When
        val result = GameCatalogResolver.resolveCatalogFromJar(tempFile)

        // Then
        Assertions.assertEquals(1, result.get().size)
        Assertions.assertEquals("ktav demo", result.get().first().name)
    }
}
