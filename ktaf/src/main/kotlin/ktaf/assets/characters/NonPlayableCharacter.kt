package ktaf.assets.characters

import ktaf.assets.Description
import ktaf.assets.Examination
import ktaf.assets.Identifier
import ktaf.assets.Item
import ktaf.assets.interaction.Interaction
import ktaf.assets.interaction.InteractionResult
import ktaf.conversations.Conversation
import ktaf.conversations.Converser

/**
 * A non-playable character with the specified [identifier] and [description].
 */
public class NonPlayableCharacter(
    override var identifier: Identifier,
    override var description: Description,
    override var conversation: Conversation = Conversation.empty,
    isAlive: Boolean = true
) : Character(), Converser {

    /**
     * A non-playable character with the specified [identifier] and [description].
     */
    public constructor(identifier: String, description: String) :
        this(Identifier(identifier), Description(description))

    /**
     * A non-playable character with the specified [identifier] and [description].
     */
    public constructor(
        identifier: Identifier,
        description: Description,
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
