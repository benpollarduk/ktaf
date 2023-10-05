package com.github.benpollarduk.ktaf.assets

import com.github.benpollarduk.ktaf.commands.CustomCommand
import com.github.benpollarduk.ktaf.extensions.ensureFinishedSentence
import com.github.benpollarduk.ktaf.extensions.removeSentenceEnd
import com.github.benpollarduk.ktaf.helpers.newline

/**
 * Provides a base implementation for examinable objects.
 */
public abstract class ExaminableObject : com.github.benpollarduk.ktaf.assets.Examinable {
    /**
     * Provides a callback for handling examination of this object.
     */
    public var examination: com.github.benpollarduk.ktaf.assets.Examination = {
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

        com.github.benpollarduk.ktaf.assets.ExaminationResult(description)
    }

    override fun toString(): String {
        return identifier.name
    }

    override var identifier: com.github.benpollarduk.ktaf.assets.Identifier =
        com.github.benpollarduk.ktaf.assets.Identifier.Companion.empty
        protected set
    override var description: com.github.benpollarduk.ktaf.assets.Description =
        com.github.benpollarduk.ktaf.assets.Description.Companion.default
    override var commands: List<CustomCommand> = emptyList()

    override fun examine(): com.github.benpollarduk.ktaf.assets.ExaminationResult {
        return examination(this)
    }

    override var isPlayerVisible: Boolean = true
}
