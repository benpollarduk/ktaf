package com.github.benpollarduk.ktaf.assets

/**
 * Provides a description of an object, specified by [description].
 */
public open class Description(description: String) {
    protected val defaultDescription: String = description

    /**
     * Get the description.
     */
    public open fun getDescription(): String {
        return defaultDescription
    }

    public companion object {
        /**
         * A default empty description.
         */
        public val default: Description =
            Description("")
    }
}
