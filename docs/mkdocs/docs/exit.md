# Exit

## Overview

An Exit is essentially a connector between to adjoining rooms.

## Use

An Exit can be simply instantiated with a direction.

```kotlin
val exit = Exit(Direction.North)
```

An Exit can be hidden from the player by setting its **isPlayerVisible** property to false. This can be set in the 
constructor.

```kotlin
val exit = Exit(Direction.NORTH, false)
```

Or set explicitly.

```kotlin
exit.isPlayerVisible = false
```

Optionally, a description of the Exit can be specified.

```kotlin
val exit = Exit(Direction.NORTH, true, Description("A door covered in ivy."))
```

This will be returned if the player examines the Exit.

Like all Examinable objects, an Exit can be assigned custom commands.

```kotlin
exit.commands = listOf(
    CustomCommand(
        CommandHelp("Shove", "Shove the door."),
        true
    ) { game, args ->
        exit.unlock()
        Reaction(ReactionResult.OK, "The door swung open.");
    }
)
```
