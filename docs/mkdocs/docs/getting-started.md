# Getting Started

## Adding the package to your project
You need to pull Ktaf into your project. The easiest way to do this is to add the package. The latest package and 
installation instructions are available [here](https://github.com/benpollarduk/ktaf/packages/).

## First Game
Once the package has been installed it's time to jump in and start building your first game.

### Setup
To start with create a new Console application. It should look something like this:

```kotlin
package com.github.benpollarduk.ktaf.gettingstarted

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

    }
}
```

### Adding a PlayableCharacter
Every game requires a character to play as, lets add that next:

```kotlin
private fun createPlayer(): PlayableCharacter {
    return PlayableCharacter("Dave", "A young boy on a quest to find the meaning of life.")
}
```

In this example whenever **createPlayer** is called a new **PlayableCharacter** will be created. The character is 
called "Dave" and has a description that describes him as "A young boy on a quest to find the meaning of life.".

### Creating the game world
The game world consists of a hierarchy of three tiers: **Overworld**, **Region** and **Room**. We will create a simple 
**Region** with two **Rooms**. We can do this directly in the **main** function for simplicity. To start with lets make 
the **Rooms**:

```kotlin
@JvmStatic
fun main(args: Array<String>) {
    val cavern = Room(
        "Cavern",
        "A dark cavern set in to the base of the mountain.",
        listOf(
            Exit(Direction.NORTH),
        ),
    )

    val tunnel = Room(
        "Tunnel",
        "A dark tunnel leading inside the mountain.",
        listOf(
            Exit(Direction.SOUTH),
        ),
    )
}
```

Although the **Rooms** haven't been added to a **Region** yet there are exits in place that will allow the player to 
move between them.

Games are boring without **Items** to interact with, let's add an item to the tunnel:

```kotlin
val holyGrail = Item("Holy Grail", "A dull golden cup, looks pretty old.", true)

tunnel.addItem(holyGrail)
```

Looking good, but the **Rooms** need to be contained within a **Region**. **RegionMaker** simplifies this process, 
but sometimes creating a **Region** directly may be more appropriate if more control is needed. Here we will 
use **RegionMaker**:

```kotlin
val regionMaker = RegionMaker("Mountain", "An imposing volcano just East of town.").also {
    it[0, 0, 0] = cavern
    it[0, 1, 0] = tunnel
}
```

This needs more breaking down. The **RegionMaker** will create a region called "Mountain" with a description of "An 
imposing volcano just East of town.". The region will contain two rooms, the cavern and the tunnel. The cavern will be 
added at position *x* 0, *y* 0, *z* 0. The tunnel will be added at position *x* 0, *y* 1, *z* 0, north of the cavern.

The game world is nearly complete, but the **Region** needs to exist within an **Overworld** for it to be finished. We 
will use **OverworldMaker** to achieve this:

```kotlin
val overworldMaker = OverworldMaker(
    "Daves World",
    "An ancient kingdom.", listOf(
        regionMaker
    )
)
```

This will create an **Overworld** called "Daves World" which is described as "An ancient kingdom" and contains a 
single **Region**.

All together the code looks like this:

```kotlin
val cavern = Room(
    "Cavern",
    "A dark cavern set in to the base of the mountain.",
    listOf(
        Exit(Direction.NORTH),
    ),
)

val tunnel = Room(
    "Tunnel",
    "A dark tunnel leading inside the mountain.",
    listOf(
        Exit(Direction.SOUTH),
    ),
)

val holyGrail = Item("Holy Grail", "A dull golden cup, looks pretty old.", true)

tunnel.addItem(holyGrail)

val regionMaker = RegionMaker("Mountain", "An imposing volcano just East of town.").also {
    it[0, 0, 0] = cavern
    it[0, 1, 0] = tunnel
}

val overworldMaker = OverworldMaker(
    "Daves World",
    "An ancient kingdom.", listOf(
        regionMaker
    )
)
```

### Checking the game is complete
For a game to come to an end it needs to either hit a game over state or a completion state.

Firstly lets look at the logic that determines if the game is complete. An **EndCheck** is required, which returns an 
**EndCheckResult** that determines if the game is complete.

In this example lets make a function that determines if the game is complete. The game is complete if the player has 
the holy grail:

```kotlin
private fun isGameComplete(game: Game): EndCheckResult {
    if (game.player.findItem("Holy Grail") == null) {
        return EndCheckResult.notEnded
    }

    return EndCheckResult(true, "Game Complete", "You have the Holy Grail!")
}
```

If the player has the holy grail then the **EndCheckResult** will return that the game has ended, and have a title that 
will read "Game Complete" and a description that reads "You have the Holy Grail!".

A common game over state may be if the player dies:

```kotlin
private fun isGameOver(game: Game): EndCheckResult {
    if (game.player.isAlive) {
        return EndCheckResult.notEnded
    }

    return EndCheckResult(true, "Game Over", "You died!")
}
```

### Creating the game
The game now has all the required assets and logic it just needs some boilerplate to tie everything together before it 
is ready to play.

A **GameCreationCallback** is required to instantiate an instance of a **Game**. This is so that new instances of the 
**Game** can be created as required.

```kotlin
val gameTemplate = object : GameTemplate() {
    override fun instantiate(ioConfiguration: IOConfiguration): Game {
        return Game(
            GameInformation(
                "The Life Of Dave",
                "Dave awakes to find himself in a cavern...",
                "A very low budget adventure.",
                "Ben Pollard"
            ),
            createPlayer(),
            overworldMaker.make(),
            { isGameComplete(it) },
            { isGameOver(it) },
            ioConfiguration = ioConfiguration
        )
    }
}
```

This requires some breaking down. A new subclass of **GameTemplate** is created. A **GameTemplate** is required to 
execute a game because the **GameExecutor**, the class responsible for executing games may need to instantiate more than 
one instance of the game. When **instantiate** is called a new instance of **Game** is returned. The constructor for  
**Game** class takes the following arguments:
**GameInformation** - information abut the game.
**Player** - the player.
**Overworld** - the overworld.
**CompletionCondition** - a callback for determining if the game is complete. Here it is passed a lambda, which in turn passes in the player to the function.
**GameOverCondition** - a callback for determining if the game is over. Here it is passed a lambda, which in turn passes in the player to the function.
**IOConfiguration** - a configuration that allows Ktaf to be used with different applications. Here the ioConfiguration which is passed in when the instantiate function is called is used.

### Executing the game
As previously mentioned, the **GameExecutor** is responsible for executing the game. **GameExecutor** can execute games 
either synchronously or asynchronously. For console applications synchronous execution is appropriate. However, for 
other types of application asynchronous execution may be required. As this is a console application synchronous execution 
is most appropriate. The **ExitMode** has been set to **RETURN_TO_TITLE_SCREEN** so that when the game ends, either on 
completion, game over or when the player chooses to exit the game by tying the **Exit** command, the application will 
return to the title screen. The **AnsiConsoleConfiguration** is used, this configures the game to be run on an ANSI 
compatible console. Other configurations can be created to allow Ktaf to function with other types of application, see 
the examples in the repo for more information.

```kotlin
GameExecutor.execute(
    gameTemplate,
    ExitMode.RETURN_TO_TITLE_SCREEN,
    AnsiConsoleConfiguration
)
```

### Bringing it all together
The full example code should look like this:

```kotlin
package com.github.benpollarduk.ktaf.gettingstarted

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.assets.characters.PlayableCharacter
import com.github.benpollarduk.ktaf.assets.locations.Direction
import com.github.benpollarduk.ktaf.assets.locations.Exit
import com.github.benpollarduk.ktaf.assets.locations.Room
import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.io.configurations.AnsiConsoleConfiguration
import com.github.benpollarduk.ktaf.logic.ExitMode
import com.github.benpollarduk.ktaf.logic.Game
import com.github.benpollarduk.ktaf.logic.GameExecutor
import com.github.benpollarduk.ktaf.logic.GameInformation
import com.github.benpollarduk.ktaf.logic.conditions.EndCheckResult
import com.github.benpollarduk.ktaf.utilities.OverworldMaker
import com.github.benpollarduk.ktaf.utilities.RegionMaker
import com.github.benpollarduk.ktaf.utilities.templates.GameTemplate

object Main {
    private fun createPlayer(): PlayableCharacter {
        return PlayableCharacter("Dave", "A young boy on a quest to find the meaning of life.")
    }

    private fun isGameComplete(game: Game): EndCheckResult {
        if (game.player.findItem("Holy Grail") == null) {
            return EndCheckResult.notEnded
        }

        return EndCheckResult(true, "Game Complete", "You have the Holy Grail!")
    }

    private fun isGameOver(game: Game): EndCheckResult {
        if (game.player.isAlive) {
            return EndCheckResult.notEnded
        }

        return EndCheckResult(true, "Game Over", "You died!")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val cavern = Room(
            "Cavern",
            "A dark cavern set in to the base of the mountain.",
            listOf(
                Exit(Direction.NORTH),
            ),
        )

        val tunnel = Room(
            "Tunnel",
            "A dark tunnel leading inside the mountain.",
            listOf(
                Exit(Direction.SOUTH),
            ),
        )

        val holyGrail = Item("Holy Grail", "A dull golden cup, looks pretty old.", true)

        tunnel.addItem(holyGrail)

        val regionMaker = RegionMaker("Mountain", "An imposing volcano just East of town.").also {
            it[0, 0, 0] = cavern
            it[0, 1, 0] = tunnel
        }

        val overworldMaker = OverworldMaker(
            "Daves World",
            "An ancient kingdom.",
            listOf(
                regionMaker,
            ),
        )

        val gameTemplate = object : GameTemplate() {
            override fun instantiate(ioConfiguration: IOConfiguration): Game {
                return Game(
                    GameInformation(
                        "The Life Of Dave",
                        "Dave awakes to find himself in a cavern...",
                        "A very low budget adventure.",
                        "Ben Pollard",
                    ),
                    createPlayer(),
                    overworldMaker.make(),
                    { isGameComplete(it) },
                    { isGameOver(it) },
                    ioConfiguration = AnsiConsoleConfiguration,
                )
            }
        }

        GameExecutor.execute(
            gameTemplate,
            ExitMode.RETURN_TO_TITLE_SCREEN,
            AnsiConsoleConfiguration,
        )
    }
}
```

Simply build and run the application and congratulations, you have a working Ktaf game!