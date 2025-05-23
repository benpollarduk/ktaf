package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.assets.attributes.AttributeManager
import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.extensions.removeSentenceEnd
import com.github.benpollarduk.ktaf.utilities.NEWLINE
import com.github.benpollarduk.ktaf.utilities.StringUtilities

/**
 * Provides a base implementation for examinable objects.
 */
public abstract class ExaminableObject : Examinable {
    public override val attributes: AttributeManager = AttributeManager()

    /**
     * Provides a callback for handling examination of this object.
     */
    public var examination: Examination = {
        var description = it.examinable.description.getDescription()

        if (it.examinable.commands.any()) {
            if (description != "") {
                description += " "
            }

            val newline = NEWLINE
            description += "$newline$newline${it.examinable.identifier.name} provides the following commands: "

            it.examinable.commands.forEach { command ->
                description += "$newline \"${command.commandHelp.command}\" - " +
                    "${command.commandHelp.description.removeSentenceEnd()}, "
            }
        }

        if (description.endsWith(", ")) {
            description = description.substring(0, description.length - 2)
            description.ensureFinishedSentence()
        }

        if (description == "") {
            description = it.examinable.identifier.name
        }

        if (description == "") {
            description = it.examinable::class.simpleName.toString()
        }

        if (attributes.count > 0) {
            description += "\n\n" + StringUtilities.getAttributesAsString(it.examinable.attributes.toMap())
        }

        ExaminationResult(description)
    }

    override fun toString(): String {
        return identifier.name
    }

    override var identifier: Identifier =
        Identifier.empty
        protected set

    override var description: Description =
        Description.default

    override var commands: List<CustomCommand> = emptyList()

    override fun examine(scene: ExaminationScene): ExaminationResult {
        return examination(ExaminationRequest(this, scene))
    }

    override var isPlayerVisible: Boolean = true
}
