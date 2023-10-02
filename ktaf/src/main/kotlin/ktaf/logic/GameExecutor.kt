package ktaf.logic

import ktaf.logic.factories.GameFactory
import java.util.concurrent.locks.ReentrantLock

public object GameExecutor {
    private val executingGames: MutableList<Game> = mutableListOf()
    private val lock = ReentrantLock()

    /**
     * Execute an instance of a [factory].
     */
    public fun execute(
        factory: GameFactory,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN
    ) {
        execute(factory.invoke(), exitMode)
    }

    /**
     * Execute an instance of a [game].
     */
    public fun execute(
        game: Game,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN
    ) {
        var run = true

        while (run) {
            try {
                lock.lock()
                executingGames.add(game)
            } finally {
                lock.unlock()
            }

            game.execute()

            when (exitMode) {
                ExitMode.EXIT_APPLICATION -> {
                    run = false
                }
                ExitMode.RETURN_TO_TITLE_SCREEN -> {
                    // nothing
                }
            }

            try {
                lock.lock()
                executingGames.remove(game)
            } finally {
                lock.unlock()
            }
        }
    }

    /**
     * Execute an instance of a [factory].
     */
    public fun executeAysnc(
        factory: GameFactory,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN
    ) {
        executeAysnc(factory.invoke(), exitMode)
    }

    /**
     * Execute an instance of a [game].
     */
    public fun executeAysnc(
        game: Game,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN
    ) {
        val gameThread = Thread { execute(game, exitMode) }
        gameThread.start()
    }

    /**
     * Cancel execution of all executing games.
     */
    public fun cancelAysnc() {
        try {
            lock.lock()
            executingGames.forEach { cancelAysnc(it) }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Cancel execution of a [game].
     */
    public fun cancelAysnc(game: Game) {
        game.end()
    }
}
