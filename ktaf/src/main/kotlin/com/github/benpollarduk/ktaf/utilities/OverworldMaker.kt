package com.github.benpollarduk.ktaf.utilities

import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.assets.locations.Overworld

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
