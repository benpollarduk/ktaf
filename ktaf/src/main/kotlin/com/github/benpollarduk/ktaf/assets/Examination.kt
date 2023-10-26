package com.github.benpollarduk.ktaf.assets

/**
 * Provides a lambda signature for a examining the [examinable] that must return a [ExaminationResult].
 */
public typealias Examination = (examinable: Examinable) -> ExaminationResult
