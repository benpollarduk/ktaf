package ktaf.logic

import ktaf.assets.characters.PlayableCharacter
import ktaf.assets.locations.Overworld

/**
 * Provides a lambda signature for creating a [Overworld] with a specified [PlayableCharacter].
 */
public typealias OverworldCreator = (PlayableCharacter) -> Overworld
