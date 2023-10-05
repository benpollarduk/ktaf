package com.github.benpollarduk.ktaf.utilities.templates

import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.logic.Game

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
