package com.github.benpollarduk.ktaf.logic.conditions

/**
 * Provides a result from a end check which details if [conditionMet] and provides a [title] and [description] to
 * add detail to the result.
 */
public class EndCheckResult(
    public val conditionMet: Boolean,
    public val title: String,
    public val description: String
) {
    public companion object {
        /**
         * Provides a default not ended result.
         */
        public val notEnded: EndCheckResult = EndCheckResult(false, "", "")
    }
}
