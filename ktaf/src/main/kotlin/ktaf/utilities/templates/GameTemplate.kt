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

    public companion object {
        /**
         * Create a new [GameTemplate] from a specified [game].
         */
        public fun fromGame(game: Game): GameTemplate {
            return object : GameTemplate() {
                override fun instantiate(ioConfiguration: IOConfiguration): Game {
                    return game
                }
            }
        }
    }
}
