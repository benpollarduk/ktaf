package com.github.benpollarduk.ktaf.assets.attributes

import com.github.benpollarduk.ktaf.extensions.insensitiveEquals

/**
 * Provides a class for managing [Attribute].
 */
public class AttributeManager {
    private val attributes: MutableMap<Attribute, Int> = mutableMapOf()

    /**
     * The number of [Attribute] managed by this [AttributeManager].
     */
    public val count: Int
        get() = attributes.size

    /**
     * Ensure an attribute with a specified [name] exists. If it doesn't it is added.
     */
    private fun ensureAttributeExists(name: String) {
        val attribute = attributes.keys.firstOrNull { it.name.insensitiveEquals(name) }

        if (attribute != null) {
            return
        }

        val newAttribute = Attribute(name, "")
        attributes[newAttribute] = 0
    }

    /**
     * Ensure a specified [attribute] exists. If it doesn't it is added.
     */
    private fun ensureAttributeExists(attribute: Attribute) {
        val match = attributes.keys.firstOrNull { it.name.insensitiveEquals(attribute.name) }

        if (match != null) {
            return
        }

        attributes[attribute] = 0
    }

    /**
     * Get an [Attribute] that matches a specified [key].
     */
    private fun getMatch(key: Attribute): Attribute {
        ensureAttributeExists(key)
        return attributes.keys.first { it.name == key.name }
    }

    /**
     * Cap a [value] between a [min] and [max] value.
     */
    private fun capValue(value: Int, min: Int, max: Int): Int {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }

    /**
     * Get all [Attribute] managed by this [AttributeManager].
     */
    public fun getAttributes(): Array<Attribute> {
        return attributes.keys.toTypedArray()
    }

    /**
     * Get all [Attribute] and values managed by this [AttributeManager].
     */
    public fun toMap(): Map<Attribute, Int> {
        return attributes.toMap()
    }

    /**
     * Get the value of an attribute from a specified [attributeName].
     */
    public fun getValue(attributeName: String): Int {
        val attribute = attributes.keys.firstOrNull { it.name.insensitiveEquals(attributeName) } ?: return 0
        return getValue(attribute)
    }

    /**
     * Get the value of an [attribute].
     */
    public fun getValue(attribute: Attribute): Int {
        return attributes[attribute] ?: 0
    }

    /**
     * Add an attribute with a specified [attributeName] and [value].
     */
    public fun add(attributeName: String, value: Int) {
        ensureAttributeExists(attributeName)
        val attribute = attributes.keys.first { it.name.insensitiveEquals(attributeName) }
        attributes[attribute] = capValue(
            attributes[attribute]?.plus(value) ?: 0,
            attribute.minimum,
            attribute.maximum
        )
    }

    /**
     * Add an [attribute] with a specified [value].
     */
    public fun add(attribute: Attribute, value: Int) {
        val matchedAttribute = getMatch(attribute)
        attributes[matchedAttribute] = capValue(
            attributes[matchedAttribute]?.plus(value) ?: 0,
            matchedAttribute.minimum,
            matchedAttribute.maximum
        )
    }

    /**
     * Subtract a [value] from an attribute with a specified [attributeName].
     */
    public fun subtract(attributeName: String, value: Int) {
        ensureAttributeExists(attributeName)
        val attribute = attributes.keys.first { it.name.insensitiveEquals(attributeName) }
        attributes[attribute] = capValue(
            attributes[attribute]?.minus(value) ?: 0,
            attribute.minimum,
            attribute.maximum
        )
    }

    /**
     * Subtract a [value] from an [attribute].
     */
    public fun subtract(attribute: Attribute, value: Int) {
        val matchedAttribute = getMatch(attribute)
        attributes[matchedAttribute] = capValue(
            attributes[matchedAttribute]?.minus(value) ?: 0,
            matchedAttribute.minimum,
            matchedAttribute.maximum
        )
    }

    /**
     * Remove an attribute with a specified [attributeName].
     */
    public fun remove(attributeName: String) {
        val attribute = attributes.keys.firstOrNull { it.name.insensitiveEquals(attributeName) }

        if (attribute != null) {
            attributes.remove(attribute)
        }
    }

    /**
     * Remove an [attribute].
     */
    public fun remove(attribute: Attribute) {
        val matchedAttribute = getMatch(attribute)
        attributes.remove(matchedAttribute)
    }

    /**
     * Remove all [attributes].
     */
    public fun removeAll() {
        attributes.clear()
    }
}
