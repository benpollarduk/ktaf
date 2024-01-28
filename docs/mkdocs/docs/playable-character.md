# PlayableCharacter

## Overview

A PlayableCharacter represents the character that the player plays as throughout the game. Each game has only a single 
PlayableCharacter.

## Use

A PlayableCharacter can be simply instantiated with a name and description.

```kotlin
val player = PlayableCharacter("Ben", "A 39 year old man.")
```

A PlayableCharacter can be also be instantiated with a list of Items.

```kotlin
val player = PlayableCharacter("Ben", "A 39 year old man.", listOf(
    Item("Guitar", "A PRS Custom 22, in whale blue, of course."),
    Item("Wallet", "An empty wallet, of course.")
)
```

A PlayableCharacter can be given items with the **acquireItem** function.

```kotlin
player.acquireItem(Item("Mallet", "A large mallet."))
```

A PlayableCharacter can lose an item with the **dequireItem** function.

```kotlin
player.dequireItem(mallet)
```

A PlayableCharacter can use an item on another asset:

```kotlin
val trapDoor = Exit(Direction.DOWN)
val mallet = Item("Mallet", "A large mallet.")
player.useItem(mallet, trapDoor)
```

A PlayableCharacter can give an item to a non-playable character.

```kotlin
val goblin = NonPlayableCharacter("Goblin", "A vile goblin.")
val daisy = Item("Daisy", "A beautiful daisy that is sure to cheer up even the most miserable creature.")
player.give(daisy, goblin)
```

PlayableCharacters can contain custom commands that allow the user to directly interact with the character or other 
assets.

```kotlin
player.commands = listOf(
    CustomCommand(
        CommandHelp("Punch wall", "Punch the wall."),
        true
    ) { game, args ->
        Reaction(ReactionResult.OK, "You punched the wall.");
    }
)
```
