package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.commands.CustomCommand

/**
 * Provides a simple contract for any object that can be examined.
 */
public interface Examinable : com.github.benpollarduk.ktaf.assets.PlayerVisible {
    /**
     * An [Identifier] that can be used to identify this object.
     */
    public val identifier: com.github.benpollarduk.ktaf.assets.Identifier

    /**
     * A [Description] that describes this object.
     */
    public val description: com.github.benpollarduk.ktaf.assets.Description

    /**
     * A list of [CustomCommand] that this [Examinable] provides.
     */
    public val commands: List<CustomCommand>

    /**
     * Examine this object to obtain an [ExaminationResult].
     */
    public fun examine(): com.github.benpollarduk.ktaf.assets.ExaminationResult
}
