package ktaf.interpretation

import ktaf.commands.Command
import ktaf.commands.game.Unactionable

/**
 * Provides the result of an interpretation. [interpretedSuccessfully] specifies if the interpretation was a success,
 * and the [command] provides the interpreted [Command].
 */
public class InterpretationResult(
    public val interpretedSuccessfully: Boolean,
    public val command: Command
) {
    public companion object {
        /**
         * Provides a default fail.
         */
        public val fail: InterpretationResult = InterpretationResult(false, Unactionable("Interpretation failed."))
    }
}
