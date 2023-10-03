package ktaf.utilities.templates

import ktaf.io.IOConfiguration
import ktaf.logic.Game

/**
 * Provides a template for producing [Game] objects.
 */
public open class GameTemplate {
    /**
     * Instantiate a new instance of the templated [Game] with a specified [ioConfiguration].
     */
    public open fun instantiate(ioConfiguration: IOConfiguration): Game {
        throw NotImplementedError()
    }
}
