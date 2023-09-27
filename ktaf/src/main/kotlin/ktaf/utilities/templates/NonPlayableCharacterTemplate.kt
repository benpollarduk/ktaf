package ktaf.utilities.templates

import ktaf.assets.characters.NonPlayableCharacter
import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Room

/**
 * Provides a template for producing [NonPlayableCharacter] objects.
 */
public open class NonPlayableCharacterTemplate {
    /**
     * Instantiate a new instance of the templated [NonPlayableCharacter] with a specified [playableCharacter] and
     * [room].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter, room: Room): NonPlayableCharacter {
        throw NotImplementedError()
    }
}
