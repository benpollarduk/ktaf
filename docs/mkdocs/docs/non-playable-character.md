# NonPlayableCharacter

## Overview

A NonPlayableCharacter represents any character that the player may meet throughout the game.

## Use

A NonPlayableCharacter can be simply instantiated with a name and description.

```kotlin
var goblin = NonPlayableCharacter("Goblin", "A vile goblin.")
```

A NonPlayableCharacter can give an item to another NonPlayableCharacter.

```kotlin
var daisy = Item("Daisy", "A beautiful daisy that is sure to cheer up even the most miserable creature.");
npc.give(daisy, goblin);
```

NonPlayableCharacters can contain custom commands that allow the user to directly interact with the character or other 
assets.

```kotlin
goblin.commands = listOf(
    CustomCommand(
        CommandHelp("Smile", "Crack a smile."),
        true
    ) { game, args ->
        Reaction(ReactionResult.OK, "Well that felt weird.");
    }
)
```

## Conversations

A NonPlayableCharacter can hold a conversation with the player. 
* A Conversation contains **Paragraphs**. 
* A Paragraph can contain one or more **Responses**.
* A **Response** can contain a delta to shift the conversation by, which will cause the conversation to jump paragraphs by the specified value.
* A **Response** can also contain a callback to perform some action when the player selects that option.

```kotlin
goblin.conversation = Conversation(
    listOf(
        Paragraph("This is a the first line."),
        Paragraph("This is a question.").also {
            it.responses = listOf(
                Response("This is the first response.", Jump(1)),
                Response("This is the second response.", Jump(2)),
                Response("This is the third response.", Jump(3)),
            )
        },
        Paragraph("You picked first response, return to start of conversation.", GoTo(1)),
        Paragraph("You picked second response, return to start of conversation.", GoTo(1)),
        Paragraph("This is the third response.") {
            it.player.kill()
        },
    ),
)
```