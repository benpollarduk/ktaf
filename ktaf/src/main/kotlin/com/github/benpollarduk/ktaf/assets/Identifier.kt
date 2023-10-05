package com.github.benpollarduk.ktaf.assets

/**
 * Provides functionality that can be used to identify an object with a specified [name].
 */
public class Identifier(public val name: String) {

    /**
     * A string that can be used for identification purposes. This provides a version of the [name] that has
     * been formatted to make string comparison simpler. This is not a unique identifier.
     */
    public val identifiableName: String = toIdentifiableString(name)

    private fun toIdentifiableString(value: String): String {
        return value.uppercase().replace(" ", "")
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is String -> name == other || identifiableName == toIdentifiableString(other)
            is Identifier -> identifiableName == other.identifiableName
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + identifiableName.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    public companion object {
        /**
         * An empty identifier.
         */
        public val empty: Identifier = Identifier("")
    }
}
