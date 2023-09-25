package ktaf.logic

import ktaf.assets.characters.PlayableCharacter

/**
 * Provides a lambda signature for creating a [PlayableCharacter].
 */
public typealias PlayableCharacterCreation = () -> PlayableCharacter
