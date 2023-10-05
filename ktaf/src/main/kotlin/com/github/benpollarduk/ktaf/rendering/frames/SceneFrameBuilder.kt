package com.github.benpollarduk.ktaf.rendering.frames

import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.assets.locations.ViewPoint
import com.github.benpollarduk.ktaf.interpretation.CommandHelp
import com.github.benpollarduk.ktaf.rendering.KeyType

/**
 * Provides a standard interface for scene frames.
 */
public interface SceneFrameBuilder {
    /**
     * Build a frame with a [room], [viewPoint], [playableCharacter], [message], [contextualCommands] and [keyType].
     */
    public fun build(
        room: Room,
        viewPoint: ViewPoint,
        playableCharacter: PlayableCharacter,
        message: String,
        contextualCommands: List<CommandHelp>,
        keyType: KeyType
    ): Frame
}
