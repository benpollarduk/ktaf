package ktaf.logic.factories

import ktaf.assets.characters.PlayableCharacter

/**
 * Provides a lambda signature for a factory for creating instances of [PlayableCharacter].
 */
public typealias PlayableCharacterFactory = () -> PlayableCharacter
