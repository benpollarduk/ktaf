package ktaf.utilities.templates

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Region

/**
 * Provides a template for producing [Region] objects.
 */
public open class RegionTemplate {
    /**
     * Instantiate a new instance of the templated [Region] with a specified [playableCharacter].
     */
    public open fun instantiate(playableCharacter: PlayableCharacter): Region {
        throw NotImplementedError()
    }
}
