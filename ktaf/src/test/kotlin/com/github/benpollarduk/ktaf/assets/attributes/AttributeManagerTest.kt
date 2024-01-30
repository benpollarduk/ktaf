package com.github.benpollarduk.ktaf.assets.attributes

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AttributeManagerTest {
    @Test
    fun `given no attributes when get attributes then return empty array`() {
        // Given
        val manager = AttributeManager()

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given one attribute when get attributes then return array with one element`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given no attributes when add then one attribute`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun givenOneAttributes_whenAddDuplicateAttribute_thenOneAttribute() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)
        manager.add("test", 0)

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given one attribute when add non duplicate attribute then two attributes`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)
        manager.add("test1", 0)

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(2, result.size)
    }

    @Test
    fun `given one attribute when add non duplicate attribute then attribute value added`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 1)
        manager.add(Attribute("test", "", 100, 2), 2)

        // When
        val result = manager.getValue("test")

        // Then
        Assertions.assertEquals(3, result)
    }

    @Test
    fun `given no attributes when remove then no attributes`() {
        // Given
        val manager = AttributeManager()
        manager.remove("test")

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given two attributes when remove then one attribute`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)
        manager.add("test1", 0)
        manager.remove(Attribute("test", "", 0, 100))

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given two attributes when remove all then no attributes`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)
        manager.add("test1", 0)
        manager.removeAll()

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given no attributes when subtract then one attribute`() {
        // Given
        val manager = AttributeManager()
        manager.subtract("test", 0)

        // When
        val result = manager.getAttributes()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given one attributes when subtract duplicate attribute then one attribute`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 0)
        manager.subtract("test", 0)

        // When
        val result = manager.count

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given one attribute when subtract attribute then attribute value subtracted`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 100)
        manager.subtract(Attribute("test", "", 100, 2), 50)

        // When
        val result = manager.getValue("test")

        // Then
        Assertions.assertEquals(50, result)
    }

    @Test
    fun `given no attributes when to map then return empty map`() {
        // Given
        val manager = AttributeManager()

        // When
        val result = manager.toMap()

        // Then
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `given one attribute when to map then return map with one element`() {
        // Given
        val manager = AttributeManager()
        manager.add("test", 1)

        // When
        val result = manager.toMap()

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given 10 when max 5 when add then value is 5`() {
        // Given
        val manager = AttributeManager()
        manager.add(Attribute("test", "", 0, 5), 10)

        // When
        val result = manager.getValue("test")

        // Then
        Assertions.assertEquals(5, result)
    }

    @Test
    fun `given minus 5 when min 0 when add then value is 0`() {
        // Given
        val manager = AttributeManager()
        manager.add(Attribute("test", "", 0, 5), -5)

        // When
        val result = manager.getValue("test")

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given subtract 10 from 5 when min 0 when subtract then value is 0`() {
        // Given
        val manager = AttributeManager()
        manager.add(Attribute("test", "", 0, 10), 5)
        manager.subtract("test", 10)

        // When
        val result = manager.getValue("test")

        // Then
        Assertions.assertEquals(0, result)
    }
}
