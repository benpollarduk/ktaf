package com.github.benpollarduk.ktaf.utilities.templates

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room

/**
 * Provides a template for producing [Item] objects.
 */
public open class ItemTemplate {
    /**
     * Instantiate a new instance of the templated [Item] with a specified [playableCharacter] and [room].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter, room: Room? = null): Item {
        throw NotImplementedError()
    }
}
