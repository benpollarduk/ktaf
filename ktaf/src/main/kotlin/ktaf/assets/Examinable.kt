package ktaf.assets

import ktaf.commands.CustomCommand

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
     * Examine this object to obtain an [ExaminationResult].
     */
    public fun examine(): ExaminationResult
}
