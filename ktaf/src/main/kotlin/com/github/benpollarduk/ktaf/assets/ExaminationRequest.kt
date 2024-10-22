package com.github.benpollarduk.ktaf.assets

/**
 * Represents a request to examine an [examinable] in a [scene].
 */
public data class ExaminationRequest(
    public val examinable: Examinable,
    public val scene: ExaminationScene
)
