package com.github.benpollarduk.ktaf.logic

import com.github.benpollarduk.ktaf.io.IOConfiguration
import com.github.benpollarduk.ktaf.utilities.templates.GameTemplate
import java.util.concurrent.locks.ReentrantLock

public object GameExecutor {
    private val executingGames: MutableList<Game> = mutableListOf()
    private val lock = ReentrantLock()

    /**
     * Execute an instance of a [Game] instantiated by a specified [template].
     */
    public fun execute(
        template: GameTemplate,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN,
        ioConfiguration: IOConfiguration
    ) {
        var run = true

        while (run) {
            val game = template.instantiate(ioConfiguration)

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
     * Execute an instance of a [Game] asynchronously, instantiated by a specified [template].
     */
    public fun executeAysnc(
        template: GameTemplate,
        exitMode: ExitMode = ExitMode.RETURN_TO_TITLE_SCREEN,
        ioConfiguration: IOConfiguration
    ) {
        val gameThread = Thread { execute(template, exitMode, ioConfiguration) }
        gameThread.start()
    }

    /**
     * Cancel execution of all executing games.
     */
    public fun cancel() {
        try {
            lock.lock()
            executingGames.forEach { cancel(it) }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Cancel execution of a [game].
     */
    public fun cancel(game: Game) {
        game.end()
    }
}
