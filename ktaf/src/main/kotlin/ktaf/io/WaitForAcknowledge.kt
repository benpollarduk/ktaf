package ktaf.io

/**
 * Provides an interface for waiting for an acknowledgement.
 */
public interface WaitForAcknowledge {
    /**
     * Wait for acknowledgment to be received. Returns true if a positive acknowledgment was received, else false.
     */
    public operator fun invoke(): Boolean
}
