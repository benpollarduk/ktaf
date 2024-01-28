# Region

## Overview

A Region is the intermediate level location in a game. An Overworld can contain multiple Regions. A Region can contain 
multiple Rooms.

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

A Region represents a 3D space. 

The **x** location always refers to the horizontal axis, with lower values being west and higher values being east.
The **y** location always refers to the vertical axis, with lower values being south and higher values being north.
The **z** location always refers to the depth axis, with lower values being down and higher values being up.

## Use

A Region can be simply instantiated with a name and description.

```kotlin
val region = Region("Name", "Description.")
```

Rooms can be added to the Region with the **addRoom** function. The x, y and z location within the Region must be 
specified.

```kotlin
region.addRoom(room, 0, 0, 0)
```

Rooms can be removed from a Region with the **removeRoom** function.

```kotlin
region.removeRoom(room)
```

The Region can be traversed with the **move** method.

```kotlin
region.move(Direction.NORTH)
```

The start position, that is the position that the Player will start in when entering a Region, can be specified 
with **setStartPosition**.

```kotlin
region.setStartPosition(0, 0, 0)
```

The **unlockDoorPair** function can be used to unlock an **Exit** in the current Room, which will also unlock the 
corresponding Exit in the adjoining **Room**.
```kotlin
region.unlockDoorPair(Direction.EAST)
```

Like all Examinable objects, Regions can be assigned custom commands.

```kotlin
region.commands = listOf(
    CustomCommand(
        CommandHelp("Warp", "Warp to the start."),
        true
    ) { game, args ->
        region.jumpToRoom(0, 0, 0)
        Reaction(ReactionResult.OK, "You warped to the start.");
    }
)
```

## RegionMaker

The RegionMaker simplifies the creation of a Region. Rooms are added to the Region with a specified **x**, **y** and 
**z** position within the Region.

```kotlin
val regionMaker = RegionMaker("Region", "Description.").also {
    it[0, 0, 0] = Room("Room 1", "Description of room 1.")
    it[1, 0, 0] = Room("Room 2", "Description of room 2.")
}
```

The main benefit of using a RegionMaker is that it allows multiple instances of a Region to be created from a single 
definition of a Region.

```kotlin
val region = regionMaker.make()
```





