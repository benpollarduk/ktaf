package ktaf.io

/**
 * Provides an interface for waiting for a command.
 */
public interface WaitForCommand {
    /**
     * Wait for a command.
     */
    public operator fun invoke(): String
}
