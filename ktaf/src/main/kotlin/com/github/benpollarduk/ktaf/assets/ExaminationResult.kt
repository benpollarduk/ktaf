package com.github.benpollarduk.ktaf.assets

/**
 * The result of an examination.
 */
public data class ExaminationResult(
    override val description: String
) : com.github.benpollarduk.ktaf.assets.interaction.Result
