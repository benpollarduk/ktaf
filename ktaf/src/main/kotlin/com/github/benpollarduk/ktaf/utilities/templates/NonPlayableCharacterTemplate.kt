package com.github.benpollarduk.ktaf.utilities.templates

import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room

/**
 * Provides a template for producing [NonPlayableCharacter] objects.
 */
public open class NonPlayableCharacterTemplate {
    /**
     * Instantiate a new instance of the templated [NonPlayableCharacter] with a specified [playableCharacter] and
     * [room].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter, room: Room? = null): NonPlayableCharacter {
        throw NotImplementedError()
    }
}
