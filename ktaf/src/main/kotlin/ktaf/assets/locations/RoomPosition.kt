package ktaf.assets.locations

/**
 * Provides an [x], [y] and [z] position for a [room].
 */
public class RoomPosition(
    public val room: Room,
    public val x: Int,
    public val y: Int,
    public val z: Int
) {
    /**
     * Determine if this is at a give [x], [y] and [z] position.
     */
    public fun isAtPosition(x: Int, y: Int, z: Int): Boolean {
        return this.x == x && this.y == y && this.z == z
    }
}
