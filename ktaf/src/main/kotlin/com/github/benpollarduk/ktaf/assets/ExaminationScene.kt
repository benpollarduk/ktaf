package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.characters.Character
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room

/**
 * Represents a scene that an examination occurs in with a [examiner] and a [room].
 */
public data class ExaminationScene(
    val examiner: Character,
    val room: Room
) {
    public companion object {
        /**
         * Provides a value representing no scene.
         */
        public val noScene: ExaminationScene = ExaminationScene(
            PlayableCharacter("", ""),
            Room("", "")
        )
    }
}
