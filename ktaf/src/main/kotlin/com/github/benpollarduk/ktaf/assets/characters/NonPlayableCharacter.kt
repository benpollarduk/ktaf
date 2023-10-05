package com.github.benpollarduk.ktaf.assets.characters

import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.Examination
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.conversations.Conversation
import com.github.benpollarduk.ktaf.conversations.Converser

/**
 * A non-playable character with the specified [identifier] and [description].
 */
public class NonPlayableCharacter(
    override var identifier: Identifier,
    override var description: com.github.benpollarduk.ktaf.assets.Description,
    override var conversation: Conversation = Conversation.empty,
    isAlive: Boolean = true
) : Character(), Converser {

    /**
     * A non-playable character with the specified [identifier] and [description].
     */
    public constructor(identifier: String, description: String) :
        this(Identifier(identifier), com.github.benpollarduk.ktaf.assets.Description(description))

    /**
     * A non-playable character with the specified [identifier] and [description].
     */
    public constructor(
        identifier: Identifier,
        description: com.github.benpollarduk.ktaf.assets.Description,
        conversation: Conversation = Conversation.empty,
        isAlive: Boolean = true,
        examination: Examination? = null,
        interaction: Interaction? = null
    ) : this(identifier, description, conversation, isAlive) {
        if (interaction != null) {
            this.interaction = interaction
        }
        if (examination != null) {
            this.examination = examination
        }
    }

    init {
        this.isAlive = isAlive
    }

    /**
     * Interact with the specified [item] to obtain a [InteractionResult].
     */
    override fun interact(item: Item): InteractionResult {
        return interactWithItem(item)
    }
}
