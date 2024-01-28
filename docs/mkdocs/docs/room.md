# Room

## Overview

A Room is the lowest level location in a game. A Region can contain multiple Rooms.

```
Overworld
├── Region
│   ├── Room
│   ├── Room
│   ├── Room
├── Region
│   ├── Room
│   ├── Room
```

A Room can contain up to six Exits, one for each of the directions **north**, **east**, **south**, **west**, **up** and 
**down**.

## Use

A Region can be simply instantiated with a name and description.

```kotlin
val room = Room("Name", "Description.")
```

Exits can be added to the Room with the **addExit** function.

```kotlin
room.addExit(Exit(Direction.EAST));
```

Exits can be removed from a Room with the **removeExit** function.

```kotlin
region.removeExit(exit);
```

Items can be added to the Room with the **addItem** function.

```kotlin
room.addItem(Item("Name", "Description."))
```

Items can be removed from a Room with the **removeItem** function.

```kotlin
region.removeItem(item)
```

Characters can be added to the Room with the **addCharacter** function.

```kotlin
room.addCharacter(NonPlayableCharacter("Name", "Description."))
```

Characters can be removed from a Room with the **removeCharacter** function.

```kotlin
region.removeCharacter(character)
```

Rooms can contain custom commands that allow the user to directly interact with the Room.

```kotlin
room.commands = listOf(
    CustomCommand(
        CommandHelp("Pull lever", "Pull the lever."),
        true
    ) { game, args ->
        val exit = room.findExit(Direction.EAST, true)
        exit.unlock()
        Reaction(ReactionResult.OK, "The exit was unlocked.");
    }
)
```
