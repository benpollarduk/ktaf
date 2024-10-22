package com.github.benpollarduk.ktaf.assets

/**
 * Provides a lambda signature for handling an [examinationRequest] that must return a [ExaminationResult].
 */
public typealias Examination = (examinationRequest: ExaminationRequest) -> ExaminationResult
