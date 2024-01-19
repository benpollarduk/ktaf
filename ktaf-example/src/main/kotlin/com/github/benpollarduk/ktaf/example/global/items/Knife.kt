package com.github.benpollarduk.ktaf.example.global.items

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.utilities.templates.AssetTemplate

internal class Knife : AssetTemplate<Item> {
    override fun instantiate(): Item {
        return Item(NAME, DESCRIPTION)
    }
    internal companion object {
        internal const val NAME = "Knife"
        private const val DESCRIPTION = "A small pocket knife."
    }
}
