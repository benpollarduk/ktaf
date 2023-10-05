package com.github.benpollarduk.ktaf.assets.locations

import com.github.benpollarduk.ktaf.assets.ConditionalDescription
import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.ExaminableObject
import com.github.benpollarduk.ktaf.assets.ExaminationResult
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.Character
import com.github.benpollarduk.ktaf.assets.characters.NonPlayableCharacter
import com.github.benpollarduk.ktaf.assets.interaction.Interaction
import com.github.benpollarduk.ktaf.assets.interaction.InteractionEffect
import com.github.benpollarduk.ktaf.assets.interaction.InteractionResult
import com.github.benpollarduk.ktaf.assets.interaction.InteractionTarget
import com.github.benpollarduk.ktaf.extensions.equalsExaminable
import com.github.benpollarduk.ktaf.extensions.getObjectifier
import com.github.benpollarduk.ktaf.extensions.isPlural
import com.github.benpollarduk.ktaf.extensions.startWithLower
import com.github.benpollarduk.ktaf.utilities.StringUtilities

/**
 * A room with a specified [identifier] and a [description].
 */
public class Room(
    override var identifier: Identifier,
    override var description: com.github.benpollarduk.ktaf.assets.Description,
    exits: List<Exit> = emptyList(),
    items: List<Item> = emptyList()
) : com.github.benpollarduk.ktaf.assets.ExaminableObject(), InteractionTarget {
    /**
     * A room with a specified [identifier] and a [description].
     */
    public constructor(
        identifier: String,
        description: String,
        exits: List<Exit> = emptyList(),
        items: List<Item> = emptyList()
    ) : this(Identifier(identifier), com.github.benpollarduk.ktaf.assets.Description(description), exits, items)

    private val _exits: MutableList<Exit> = mutableListOf()
    private val _characters: MutableList<NonPlayableCharacter> = mutableListOf()
    private val _items: MutableList<Item> = mutableListOf()

    init {
        for (exit in exits) {
            _exits.add(exit)
        }

        for (item in items) {
            _items.add(item)
        }
    }

    /**
     * Get the [Exit] at the specified [direction]. If no [Exit] exists in the specified [direction] then null is
     * returned.
     */
    public operator fun get(direction: Direction): Exit? {
        return exits.firstOrNull { it.direction == direction }
    }

    /**
     * Get the all exits.
     */
    public val exits: List<Exit>
        get() = _exits.toList()

    /**
     * Get the all unlocked exits.
     */
    public val unlockedExits: List<Exit>
        get() = _exits.filterNot { it.isLocked }.toList()

    /**
     * Get the all characters in this [Room].
     */
    public val characters: List<NonPlayableCharacter>
        get() = _characters.toList()

    /**
     * Get the all items in this [Room].
     */
    public val items: List<Item>
        get() = _items.toList()

    /**
     * Specifies how this [Room] interacts with various [Item].
     */
    public var interaction: Interaction = { item, _ ->
        InteractionResult(InteractionEffect.NO_EFFECT, item)
    }

    /**
     * Get if this [Room] has been visited.
     */
    public var hasBeenVisited: Boolean = false
        private set

    /**
     * Get the [Direction] this room was entered from.
     */
    public var enteredFrom: Direction? = null
        private set

    /**
     * Add a [character] to this [Room].
     */
    public fun addCharacter(character: NonPlayableCharacter) {
        if (!_characters.contains(character)) {
            _characters.add(character)
        }
    }

    /**
     * Remove a [character] from this [Room].
     */
    public fun removeCharacter(character: NonPlayableCharacter) {
        _characters.remove(character)
    }

    /**
     * Add an [item] to this [Room].
     */
    public fun addItem(item: Item) {
        if (!_items.contains(item)) {
            _items.add(item)
        }
    }

    /**
     * Remove an [item] from this [Room].
     */
    public fun removeItem(item: Item) {
        _items.remove(item)
    }

    /**
     * Move in to this [Room] form a specified [Direction].
     */
    public fun movedInto(from: Direction?) {
        enteredFrom = from
        hasBeenVisited = true
    }

    /**
     * Determine if a move in this [Room] is possible in a specified [direction]. Returns true if the move is possible,
     * else false.
     */
    public fun canMove(direction: Direction): Boolean {
        return unlockedExits.any { it.direction == direction }
    }

    /**
     * Add an [exit] to this [Room].
     */
    public fun addExit(exit: Exit) {
        if (!_exits.contains(exit)) {
            _exits.add(exit)
        }
    }

    /**
     * Remove an [exit] from this [Room].
     */
    public fun removeExit(exit: Exit) {
        _exits.remove(exit)
    }

    /**
     * Remove a [target] from this [Room]. If the [target] exists in the [Room] true is returned else false.
     */
    public fun removeInteractionTarget(target: InteractionTarget): Boolean {
        if (_items.contains(target)) {
            removeItem(target as Item)
            return true
        }

        if (_characters.contains(target)) {
            removeCharacter(target as NonPlayableCharacter)
            return true
        }

        return false
    }

    /**
     * Get if this [Room] has a locked [Exit] in a specified [direction]. If [includeInvisibleExits] is true then exits
     * that aren't visible to the player are included.
     */
    public fun hasLockedExitInDirection(direction: Direction, includeInvisibleExits: Boolean = false): Boolean {
        return exits.any { it.direction == direction && it.isLocked && (includeInvisibleExits || it.isPlayerVisible) }
    }

    /**
     * Get if this [Room] has an unlocked [Exit] in a specified [direction]. If [includeInvisibleExits] is true then
     * exits that aren't visible to the player are included.
     */
    public fun hasUnlockedExitInDirection(direction: Direction, includeInvisibleExits: Boolean = false): Boolean {
        return exits.any { it.direction == direction && !it.isLocked && (includeInvisibleExits || it.isPlayerVisible) }
    }

    /**
     * Get if this [Room] has an [Exit] in a specified [direction]. If [includeInvisibleExits] is true then
     * exits that aren't visible to the player are included.
     */
    public fun hasExit(direction: Direction, includeInvisibleExits: Boolean = false): Boolean {
        return exits.any { it.direction == direction && (includeInvisibleExits || it.isPlayerVisible) }
    }

    /**
     * Find the [Exit] in the specified [direction]. If [includeInvisibleExits] is set true then exits which are not
     * player visible will be included. If an exits cannot be found then null is returned.
     */
    public fun findExit(direction: Direction, includeInvisibleExits: Boolean = false): Exit? {
        return exits.firstOrNull { it.direction == direction && (includeInvisibleExits || it.isPlayerVisible) }
    }

    /**
     * Get if this [Room] contains a specified [item].
     */
    public fun containsItem(item: Item): Boolean {
        return items.contains(item)
    }

    /**
     * Get if this [Room] contains an [item]. If [includeInvisibleItems] is true then items that aren't visible to the
     * player are included.
     */
    public fun containsItem(item: String, includeInvisibleItems: Boolean = false): Boolean {
        return items.any { item.equalsExaminable(it) && (includeInvisibleItems || it.isPlayerVisible) }
    }

    /**
     * Find an [Item] in this [Room], specified by [item]. If [includeInvisibleItems] is true then items that aren't
     * visible to the player are included.
     */
    public fun findItem(item: String, includeInvisibleItems: Boolean = false): Item? {
        return items.firstOrNull { item.equalsExaminable(it) && (includeInvisibleItems || it.isPlayerVisible) }
    }

    /**
     * Get if this [Room] contains an [InteractionTarget] specified by [target]. If [includeInvisibleExits] is true
     * then targets that aren't visible to the player are included.
     */
    public fun containsInteractionTarget(target: String, includeInvisibleExits: Boolean = false): Boolean {
        return items.any { target.equalsExaminable(it) && (includeInvisibleExits || it.isPlayerVisible) } ||
            characters.any { target.equalsExaminable(it) && (includeInvisibleExits || it.isPlayerVisible) }
    }

    /**
     * Find an [InteractionTarget] in this [Room], specified by [target]. If [includeInvisibleTargets] is true then
     * targets that aren't visible to the player are included.
     */
    public fun findInteractionTarget(target: String, includeInvisibleTargets: Boolean = false): InteractionTarget? {
        val item = items.firstOrNull { target.equalsExaminable(it) && (includeInvisibleTargets || it.isPlayerVisible) }
        return item ?: characters.firstOrNull {
            target.equalsExaminable(it) && (includeInvisibleTargets || it.isPlayerVisible)
        }
    }

    /**
     * Get if this [Room] contains a specified [character].
     */
    public fun containsCharacter(character: NonPlayableCharacter): Boolean {
        return characters.contains(character)
    }

    /**
     * Get if this [Room] contains a [character]. If [includeInvisibleCharacters] is true then characters that aren't
     * visible to the player are included.
     */
    public fun containsCharacter(character: String, includeInvisibleCharacters: Boolean = false): Boolean {
        return characters.any { character.equalsExaminable(it) && (includeInvisibleCharacters || it.isPlayerVisible) }
    }

    /**
     * Find a [Character] in this [Room], specified by [character]. If [includeInvisibleCharacters] is true then items
     * that aren't visible to the player are included.
     */
    public fun findCharacter(character: String, includeInvisibleCharacters: Boolean = false): NonPlayableCharacter? {
        return characters.firstOrNull {
            character.equalsExaminable(it) && (includeInvisibleCharacters || it.isPlayerVisible)
        }
    }

    /**
     * Specify a [ConditionalDescription] for this [Room] with [description].
     */
    public fun specifyConditionalDescription(description: com.github.benpollarduk.ktaf.assets.ConditionalDescription) {
        this.description = description
    }

    override fun examine(): ExaminationResult {
        val visibleItems = items.filter { it.isPlayerVisible }

        if (visibleItems.isEmpty()) {
            return ExaminationResult("There is nothing to examine.")
        }

        if (visibleItems.size == 1) {
            val item = visibleItems.first()
            return ExaminationResult(
                "There ${if (item.identifier.name.isPlural()) "are" else "is"} " +
                    "${item.identifier.name.getObjectifier()} ${item.identifier}"
            )
        }

        val sentence = StringUtilities.constructExaminablesAsSentence(items)
        val endIndex = when (sentence.contains(", ")) {
            true -> sentence.indexOf(", ")
            false -> sentence.indexOf(" and ")
        }
        val firstItemName = sentence.substring(0, endIndex)
        return ExaminationResult(
            "There ${if (firstItemName.isPlural()) "are" else "is"} ${sentence.startWithLower()}()"
        )
    }

    override fun interact(item: Item): InteractionResult {
        return interaction.invoke(item, this)
    }

    public companion object {
        /**
         * An empty [Room].
         */
        public val empty: Room = Room("", "")
    }
}
