package com.github.benpollarduk.ktaf.utilities.templates

/**
 * Provides an interface for instantiating templated assets.
 */
public fun interface AssetTemplate<T> {
    /**
     * Instantiate a new instance of the asset.
     */
    public fun instantiate(): T
}
