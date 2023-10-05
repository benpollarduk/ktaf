package com.github.benpollarduk.ktaf.utilities.templates

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room

/**
 * Provides a template for producing [Room] objects.
 */
public open class RoomTemplate {
    /**
     * Instantiate a new instance of the templated [Room] with a specified [playableCharacter].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter): Room {
        throw NotImplementedError()
    }
}
