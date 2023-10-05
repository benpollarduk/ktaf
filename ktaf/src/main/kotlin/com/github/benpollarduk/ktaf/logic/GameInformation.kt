package com.github.benpollarduk.ktaf.logic

/**
 * Provides core information about a [Game].
 */
public data class GameInformation(
    public val name: String,
    public val introduction: String,
    public val description: String,
    public val author: String
)
