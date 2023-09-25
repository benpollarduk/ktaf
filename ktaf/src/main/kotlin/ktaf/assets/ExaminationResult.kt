package ktaf.assets

/**
 * The result of an examination.
 */
public data class ExaminationResult(
    override val description: String
) : ktaf.assets.interaction.Result
