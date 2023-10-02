package ktaf.logic

import ktaf.io.IOConfiguration
import ktaf.logic.factories.GameFactory

/**
 * Provides a wrapper for a [Game]. This allows a [Game] inside a jar to be exposed to ktaf.
 */
public interface GameWrapper {
    /**
     * Get a [GameFactory] used to provide an instance of the [Game], with a specified [IOConfiguration].
     */
    public fun get(ioConfiguration: IOConfiguration): GameFactory
}
