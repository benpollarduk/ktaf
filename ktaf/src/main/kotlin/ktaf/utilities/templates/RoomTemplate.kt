package ktaf.utilities.templates

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room

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
