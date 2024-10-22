package com.github.benpollarduk.ktaf.assets.locations

import com.github.benpollarduk.ktaf.assets.Description
import com.github.benpollarduk.ktaf.assets.ExaminableObject
import com.github.benpollarduk.ktaf.assets.ExaminationResult
import com.github.benpollarduk.ktaf.assets.ExaminationScene
import com.github.benpollarduk.ktaf.assets.Identifier
import com.github.benpollarduk.ktaf.extensions.equalsExaminable

/**
 * Provides an overworld which is a container of [Region]. The overwold has an [identifier] and a [description].
 */
public class Overworld(
    override var identifier: Identifier,
    override var description: Description
) : ExaminableObject() {
    /**
     * Provides an overlord which is a container of [Region]. The overwold has an [identifier] and a [description].
     */
    public constructor(
        identifier: String,
        description: String
    ) : this(Identifier(identifier), Description(description))

    private val mutableRegions: MutableList<Region> = mutableListOf()
    private var _currentRegion: Region? = null

    /**
     * Returns a list of [Region] that make up this [Overworld].
     */
    public val regions: List<Region>
        get() = mutableRegions.toList()

    /**
     * Returns the current [Region].
     */
    public var currentRegion: Region?
        get() {
            if (_currentRegion != null) {
                return _currentRegion
            }
            if (regions.isNotEmpty()) {
                return regions.first()
            }
            return null
        }
        private set(value) {
            _currentRegion = value
        }

    /**
     * Add a [region] to this [Overworld].
     */
    public fun addRegion(region: Region) {
        mutableRegions.add(region)
    }

    /**
     * Remove a [region] from this [Overworld].
     */
    public fun removeRegion(region: Region) {
        mutableRegions.remove(region)

        if (_currentRegion == region) {
            _currentRegion = null
        }
    }

    /**
     * Find a [Region] within this [Overworld], by [regionName].
     */
    public fun findRegion(regionName: String): Region? {
        return regions.firstOrNull { regionName.equalsExaminable(it) }
    }

    /**
     * Move to a specified [region]. Returns true if the move was successful, else false.
     */
    public fun move(region: Region): Boolean {
        if (!regions.contains(region)) {
            return false
        }
        currentRegion = region
        return true
    }

    override fun examine(scene: ExaminationScene): ExaminationResult {
        return ExaminationResult(description.getDescription())
    }
}
