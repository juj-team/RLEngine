package items.depers

import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object BottleTotemItem: DepersTotemItem {
    override val itemName: String = "Бутылёк"
    override val model: Int = 2
    override val id: String = "depers_bottle"
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 1))
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        // do nothing
    }

}