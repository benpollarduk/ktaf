package ktaf.utilities.templates

import ktaf.assets.Item

public class Spanner : ItemTemplate<Spanner>() {
    override fun onCreate(): Item {
        return Item(name, description)
    }

    public companion object {
        internal const val name = "Spanner"
        private const val description = "A metal spanner."
    }
}
