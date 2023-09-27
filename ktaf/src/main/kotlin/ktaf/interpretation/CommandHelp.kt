package ktaf.interpretation

/**
 * Provides a [description] for a [command].
 */
public class CommandHelp(public val command: String, public val description: String) {

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is CommandHelp -> command == other.command && description == other.description
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + command.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
