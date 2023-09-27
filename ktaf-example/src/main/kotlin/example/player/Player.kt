package example.player

import ktaf.assets.characters.PlayableCharacter
import ktaf.utilities.templates.PlayableCharacterTemplate

internal class Player : PlayableCharacterTemplate() {
    override fun instantiate(): PlayableCharacter {
        return PlayableCharacter(NAME, DESCRIPTION)
    }

    internal companion object {
        internal const val NAME = "Ben"
        private const val DESCRIPTION = "You are a 25 year old man, dressed in shorts, a t-shirt and flip-flops."
    }
}
