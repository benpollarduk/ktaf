package com.github.benpollarduk.ktaf.example.global.items

import com.github.benpollarduk.ktaf.assets.Item
import com.github.benpollarduk.ktaf.utilities.templates.ItemTemplate

internal class Knife : ItemTemplate() {
    override fun instantiate(): Item {
        return Item(NAME, DESCRIPTION)
    }
    internal companion object {
        internal const val NAME = "Knife"
        private const val DESCRIPTION = "A small pocket knife."
    }
}
