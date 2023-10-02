package ktaf.io

import ktaf.logic.CancellationToken

/**
 * Provides an interface for waiting for an acknowledgement.
 */
public interface WaitForAcknowledge {
    /**
     * Wait for acknowledgment to be received. Returns true if a positive acknowledgment was received, else false. A
     * [cancellationToken] can be provided to accommodate cancellation of the invocation.
     */
    public operator fun invoke(cancellationToken: CancellationToken): Boolean
}
