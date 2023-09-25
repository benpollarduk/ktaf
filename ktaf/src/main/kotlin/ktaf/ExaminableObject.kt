package ktaf

import ktaf.assets.Description
import ktaf.assets.Examinable
import ktaf.assets.Examination
import ktaf.assets.ExaminationResult
import ktaf.assets.Identifier
import ktaf.commands.CustomCommand
import ktaf.extensions.ensureFinishedSentence
import ktaf.extensions.removeSentenceEnd
import ktaf.helpers.newline

/**
 * Provides a base implementation for examinable objects.
 */
public abstract class ExaminableObject : Examinable {
    /**
     * Provides a callback for handling examination of this object.
     */
    public var examination: Examination = {
        var description = it.description.getDescription()

        if (it.commands.any()) {
            if (description != "") {
                description += " "
            }

            val newline = newline()
            description += "$newline$newline${it.identifier.name} provides the following commands: "

            it.commands.forEach { command ->
                description += "$newline \"${command.commandHelp.command}\" - " +
                    "${command.commandHelp.description.removeSentenceEnd()}, "
            }

            if (description.endsWith(", ")) {
                description = description.substring(0, description.length - 2)
                description.ensureFinishedSentence()
            }

            if (description == "") {
                description = it::class.simpleName.toString()
            }
        }

        ExaminationResult(description)
    }

    override fun toString(): String {
        return identifier.name
    }

    override var identifier: Identifier = Identifier.empty
        protected set
    override var description: Description = Description.default
    override var commands: List<CustomCommand> = emptyList()

    override fun examine(): ExaminationResult {
        return examination(this)
    }

    override var isPlayerVisible: Boolean = true
}
