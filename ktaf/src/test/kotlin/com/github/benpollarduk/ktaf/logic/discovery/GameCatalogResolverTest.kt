package com.github.benpollarduk.ktaf.logic.discovery

import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

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

    @Test
    fun `given example jar containing one game template object when resolving catalog entries for directory then one entry returned`() {
        // Given
        val testJarStream = javaClass.classLoader.getResourceAsStream("test.jar")
        val tempDir = Files.createTempDirectory("tempDir").toFile()
        tempDir.deleteOnExit()

        val tempFile = File(tempDir, "test.jar")

        // write the contents of the resource to the temporary file
        testJarStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }

        // create a test room
        val room = Room("Test Room", "A test room.")

        // add an exit to the north
        room.addExit(Exit(Direction.NORTH))

        // When
        val result = GameCatalogResolver.resolveCatalogFromDirectory(tempDir.absolutePath)

        // Then
        Assertions.assertEquals(1, result.get().size)
        Assertions.assertEquals("ktav demo", result.get().first().name)
    }
}
