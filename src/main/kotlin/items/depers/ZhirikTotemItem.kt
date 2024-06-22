package items.depers

import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffectType

object ZhirikTotemItem: DepersTotemItem {
    override val itemName: String = "Жирик"
    override val model: Int = 8
    override val id: String = "depers_zhirik"
    

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        if(player.hasPotionEffect(PotionEffectType.LEVITATION))
            player.removePotionEffect(PotionEffectType.LEVITATION)
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        // do nothing
    }

}