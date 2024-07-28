package util

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.LivingEntity

object BlockUtils {
    fun iterateOverBlocks(entity: LivingEntity, rx: IntRange, ry: IntRange, rz: IntRange) : Sequence<Block> {
        return sequence {
            for (dx in rx) {
                for (dy in ry) {
                    for (dz in rz) {
                        val playerLocation = entity.location
                        val blockLocation = Location(
                            entity.world,
                            playerLocation.x + dx,
                            playerLocation.y + dy,
                            playerLocation.z + dz,
                        )

                        yield(blockLocation.block)
                    }
                }
            }
        }
    }

    fun iterateOverMaterialsInLine(loc1: Location, loc2: Location): Sequence<Material>? {
        if (loc1.world != loc2.world) return null

        val center1 = loc1.toCenterLocation().toVector()
        val center2 = loc2.toCenterLocation().toVector()


        val direction = center2.clone().subtract(center1)
        val distance = direction.length()
        val directionNormalized = direction.normalize()

        var currentDistance = 0.0

        return sequence {
            while (currentDistance <= distance) {
                val currentVector = directionNormalized.clone().multiply(currentDistance)
                val location = Location(
                    loc1.world,
                    center1.x + currentVector.x,
                    center1.y + currentVector.y,
                    center1.z + currentVector.z,
                )

                yield(location.block.type)

                currentDistance += 0.7
            }
        }
    }
}