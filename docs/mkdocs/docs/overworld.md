# Overworld

## Overview

An Overworld is the top level location in a game. A game can only contain a single Overworld. An Overworld can contain 
multiple Regions.

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

## Use

An Overworld can be simply instantiated with a name and description.

```kotlin
val overworld = Overworld("Name", "Description.")
```

Regions can be added to the Overworld with the **addRegion** function.

```kotlin
overworld.addRegion(region)
```

Regions can be removed from an Overworld with the **removeRegion** function.

```kotlin
overworld.removeRegion(region)
```

The Overworld can be traversed with the **move** function.

```kotlin
overworld.move(region)
```

## OverworldMaker

The OverworldMaker simplifies the creation of the Overworld, when used in conjunction with RegionMakers.

```kotlin
val overworldMaker = OverworldMaker("Name", "Description.", regionMakers)
```

However, the main benefit of using an OverworldMaker is that it allows multiple instances of an Overworld to be created 
from a single definition of an Overworld.

```kotlin
val overworld = overworldMaker.make()
```



