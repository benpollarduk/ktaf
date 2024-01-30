package com.github.benpollarduk.ktaf.assets.attributes

/**
 * An attribute with a specified [name], [description] and an optional [minimum] and [maximum] value.
 */
public data class Attribute(
    public val name: String,
    public val description: String,
    public val minimum: Int = Int.MIN_VALUE,
    public val maximum: Int = Int.MAX_VALUE
)
