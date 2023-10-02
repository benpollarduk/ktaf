package ktaf.logic.factories

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Overworld

/**
 * Provides a lambda signature for a factory that produces instances of [Overworld] with a specified
 * [PlayableCharacter].
 */
public typealias OverworldFactory = (PlayableCharacter) -> Overworld
