package ktaf.utilities

import ktaf.assets.Description
import ktaf.assets.Identifier
import ktaf.assets.locations.Overworld

/**
 * A helper for making an [Overworld], with a specified [identifier], [description] and [regionMakers].
 */
public class OverworldMaker(
    private val identifier: Identifier,
    private val description: Description,
    private val regionMakers: List<RegionMaker> = emptyList()
) {
    /**
     * A helper for making an [Overworld], with a specified [identifier], [description] and [regionMakers].
     */
    public constructor(
        identifier: String,
        description: String,
        regionMakers: List<RegionMaker> = emptyList()
    ) : this(Identifier(identifier), Description(description), regionMakers)

    /**
     * Make a new [Overworld].
     */
    public fun make(): Overworld {
        val overworld = Overworld(identifier, description)

        for (regionMaker in regionMakers) {
            overworld.addRegion(regionMaker.make())
        }

        return overworld
    }
}
