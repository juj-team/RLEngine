package listeners

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Boat
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPlaceEvent

object FlyingBoatPlacementListener: Listener {
    @EventHandler
    fun onFlyingBoatPlace(event: EntityPlaceEvent){
        val player = event.player
        player ?: return

        val placedItem = player.inventory.getItem(event.hand)
        if(placedItem.type !in listOf(Material.ACACIA_BOAT, Material.ACACIA_CHEST_BOAT)) return

        val block = event.block
        val spawnLoc = Location(player.world, block.location.blockX.toDouble(),
            (block.location.blockY+1).toDouble(), block.location.blockZ.toDouble()
        )
        //boat type selector
        val boat = when(placedItem.type){
            Material.ACACIA_BOAT -> {
                player.world.spawnEntity(spawnLoc, EntityType.BOAT)
            }

            Material.ACACIA_CHEST_BOAT -> {
                player.world.spawnEntity(spawnLoc, EntityType.CHEST_BOAT)
            }

            else -> {
                //fail over
                Bukkit.getLogger().warning("Flying boat placement has failed!")
                return
            }
        }
        placedItem.amount = 0

        (boat as Boat).boatType = Boat.Type.ACACIA
        boat.setGravity(false)
        event.isCancelled = true
    }
}