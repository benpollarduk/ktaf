package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.attributes.AttributeManager
import com.github.benpollarduk.ktaf.commands.CustomCommand

/**
 * Provides a simple contract for any object that can be examined.
 */
public interface Examinable : PlayerVisible {
    /**
     * An [Identifier] that can be used to identify this object.
     */
    public val identifier: Identifier

    /**
     * A [Description] that describes this object.
     */
    public val description: Description

    /**
     * A list of [CustomCommand] that this [Examinable] provides.
     */
    public val commands: List<CustomCommand>

    /**
     * An [AttributeManager] that provides management of all [Attribute] for this [Examinable].
     */
    public val attributes: AttributeManager

    /**
     * Examine this object to obtain an [ExaminationResult].
     */
    public fun examine(): ExaminationResult
}
