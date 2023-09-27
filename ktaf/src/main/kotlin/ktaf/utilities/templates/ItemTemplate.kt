package ktaf.utilities.templates

import ktaf.assets.Item
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room

/**
 * Provides a template for producing [Item] objects.
 */
public open class ItemTemplate {
    /**
     * Instantiate a new instance of the templated [Item] with a specified [playableCharacter] and [room].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter, room: Room): Item {
        throw NotImplementedError()
    }
}
