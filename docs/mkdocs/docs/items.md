# Item

## Overview

Items can be used to add interactivity with a game. Items can be something that a player can take with them, or they may be static in a Room.

## Use

An Item can be simply instantiated with a name and description.

```kotlin
val sword = Item("Sword", "A heroes sword.")
```

By default, an Item is not takeable and is tied to a Room. If it is takeable this can be specified in the constructor.

```kotlin
val sword = Item("Sword", "A heroes sword.", true)
```

An Item can morph in to another Item. This is useful in situations where the Item changes state. Morphing is invoked 
with the **morph** function. The Item that morph is invoked on takes on the properties of the Item being morphed into.

```kotlin
val brokenSword = Item("Broken Sword", "A broken sword")
sword.morph(brokenSword)
```

Like all Examinable objects, an Item can be assigned custom commands.

```kotlin
bomb.commands = listOf(
    CustomCommand(
        CommandHelp("Cut wire", "Cut the red wire."),
        true
    ) { game, args ->
        game.player.kill()
        Reaction(ReactionResult.OK, "Boom!")
    }
)
```

## Interaction

Interactions can be set up between different assets in the game. The **InteractionResult** contains the result of the 
interaction, and allows the game to react to the interaction.

```kotlin
val dartsBoard = Item("Darts board", "A darts board.")

val dart = Item("Dart", "A dart").apply {
    interaction = {
        if (it == dartsBoard) {
            InteractionResult(
                InteractionEffect.SELF_CONTAINED,
                it,
                "The dart stuck in the darts board.",
            )
        }

        InteractionResult(InteractionEffect.NO_EFFECT, it)
    }
}
```