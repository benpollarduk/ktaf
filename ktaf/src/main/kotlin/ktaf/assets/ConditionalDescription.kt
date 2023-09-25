package ktaf.assets

/**
 * Provides a conditional description of an object. Returns the [trueDescription] if the [condition] returns true,
 * else the [falseDescription].
 */
public class ConditionalDescription(
    trueDescription: String,
    private val falseDescription: String,
    private val condition: Condition
) : Description(trueDescription) {

    override fun getDescription(): String {
        return if (condition.invoke()) {
            defaultDescription
        } else {
            falseDescription
        }
    }
}
