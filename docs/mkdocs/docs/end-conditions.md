# End Conditions

## Overview
The **EndCheck** class allows the game to determine if it has come to an end. Each game has two end conditions
* **GameOverCondition** when the game is over, but has not been won.
* **CompletionCondition** when the game is over because it has been won.

## Use
When an **EndCheck** is invoked it returns an **EndCheckResult**. The **EndCheckResult** details the result of the check
to see if the game has ended.

```kotlin
fun isGameOver(Game game): EndCheckResult
{
    if (game.player.isAlive)
        return EndCheckResult.NotEnded

    return EndCheckResult(true, "Game Over", "You died!")
}
```

The **GameOverCondition** and **CompletionCondition** are passed in to the game as arguments when a game is created.
