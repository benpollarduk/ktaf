package ktaf.io

import ktaf.logic.CancellationToken

/**
 * Provides an interface for waiting for a command.
 */
public interface WaitForCommand {
    /**
     * Wait for a command. A [cancellationToken] can be provided to accommodate cancellation of the invocation.
     */
    public operator fun invoke(cancellationToken: CancellationToken): String
}
