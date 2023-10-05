package com.github.benpollarduk.ktaf.logic

/**
 * Provides a token for allowing cancellation of an operation.
 */
public class CancellationToken {
    private var _wasCancelled: Boolean = false

    /**
     * Get if the operation was cancelled.
     */
    public val wasCancelled: Boolean
        get() = _wasCancelled

    /**
     * Cancel.
     */
    public fun cancel() {
        _wasCancelled = true
    }
}
