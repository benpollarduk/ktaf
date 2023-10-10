# Introduction 
An implementation of the BP.AdventureFramework - a framework for building text based adventures - in Kotlin. Ktaf aims to provide all of the basic building blocks required to start writing simple games.

Included in the repo are example projects that show how ktaf can be used to write games that run in a terminal window as originally intended, and also an example Swing application and an example Ktor webserver are also included that render the game in HTML.

![image](https://github.com/ben-pollard-uk/ktaf/assets/129943363/27670c5d-7f4f-4534-93cb-7931fd8d90e4)

Ktaf provides simple classes for developing game elements:
  * Interface and base class for examinable objects:
    * Examination returns a description of the object.
    * Descriptions can be conditional, with different results generated from the game state.
    * All items can contain custom commands.
  * Hierarchical environments:
    * Overworld
      * Acts as a container of regions.
    * Region
      * Acts as a container of rooms.
    * Room
      * The player traverses through the rooms.
      * Provides a description of itself.
      * Supports up to 6 exits. Each exit can be locked until a condition is met.
      * Can contain multiple items.
  * NPC's:
    * Support provided for conversations with the player.
    * Can interact with items.
    * Can contain multiple items.
  * Items
    * Support interaction with the player, rooms, other items and NPC's.
    * Items can morph in to other items. For example, using item A on item B may cause item B to morph into item C.
  
The framework provides keywords for interacting with game elements:
  * Drop - drop an item.
  * Examine - allows items, characters and environments to be examined.
  * Take - take an item.
  * Talk to - talk to a NPC.
  * Use on - use an item. Items can be used on a variety of targets.
  * N, S, E, W, U, D - traverse through the rooms in a region.

Interpretation can be easily extended with the ability for custom interpreters and commands to be added outside of the core Ktor library.

Conversations with NPC's can be entered in to with an easy to use interface to display dialogue and provide responses:

![image](https://github.com/ben-pollard-uk/ktaf/assets/129943363/3adc4210-2732-4f79-9d19-000af0287f07)
  
The framework also provides global commands to help with game flow and option management:
  * About - display version information.
  * CommandsOn / CommandsOff - toggle commands on/off.
  * Exit - exit the game.
  * Help - display the help screen.
  * KeyOn / KeyOff - turn the Key on/off.
  * Map - display the map.
  * New - start a new game.

All game management is provided by the framework, including:
  * Rendering of game screens:
    * Default frame.
    * Help frame.
    * Map frame.
    * Title frame.
    * Completion frame.
    * Game over frame.
    * Transition frame.
    * Conversation frame.
  * Input parsing.
  * State management.
  * Game creation.
  
Maps are automatically generated for regions and rooms:

![image](https://github.com/ben-pollard-uk/ktaf/assets/129943363/b8e52974-dad7-4c27-8c0a-6861964a2fbe)

Precompiled games can also be discovered and loaded from .jar files at runtime with the GameCatalogResolver.
```Kotlin
// load a .jar file and discover all GameTemplate instances
val catalog = GameCatalogResolver.resolveCatalogFromJar(File(path))
// grab the first GameTemplate
val template = catalog.get().first()
// begin execution of the game
GameExecutor.execute(gameTemplate, ioConfiguration = AnsiConsoleConfiguration)
```

# Documentation
Please visit [https://benpollarduk.github.io/ktaf-docs/](https://benpollarduk.github.io/ktaf-docs/) to view the ktaf documentation.

# Prerequisites
The default frame collections for rendering in a terminal assume that a terminal capable of handling ANSI is bring used. If a terminal that doesn't support ANSI is used the game will still render but ANSI will also be displayed.

# Getting Started
 * Clone the repo
 * Run the included terminal application
```bash
./gradlew :app-ktaf-example-console:run
```
 * Run included Swing example application to run with a basic UI
```bash
./gradlew :app-ktaf-example-swing:run
```
 * Run included ktor example webserver then navigate to localhost:8080 in browser
```bash
./gradlew :app-ktaf-example-ktor:run
```

# Hello World
```kotlin
// create a template for creating games
val template = object : GameTemplate() {
    override fun instantiate(ioConfiguration: IOConfiguration): Game {
        // create player
        val player = PlayableCharacter(
            "Dave",
            "A young boy on a quest to find the meaning of life.",
        )

        // create region maker and add room
        val regionMaker = RegionMaker(
            "Mountain",
            "An imposing volcano just East of town.",
        ).also {
            it[0, 0, 0] = Room(
                "Cavern",
                "A dark cavern set in to the base of the mountain.",
            )
        }

        // create overworld maker
        val overworldMaker = OverworldMaker(
            "Daves World",
            "An ancient kingdom.",
            listOf(regionMaker),
        )

        // create game
        return Game(
            GameInformation(
                "The Life Of Dave",
                "Dave awakes to find himself in a cavern...",
                "A very low budget adventure.",
                "Me",
            ),
            player,
            overworldMaker.make(),
            { EndCheckResult.notEnded },
            { EndCheckResult.notEnded },
        )
    }
}

// execute the game
GameExecutor.execute(template, ioConfiguration = ioConfiguration)
```

# For Open Questions
Visit https://github.com/ben-pollard-uk/ktaf/issues
