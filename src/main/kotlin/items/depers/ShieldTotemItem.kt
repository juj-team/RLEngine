package items.depers

import util.RLEngineTaskManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object ShieldTotemItem: DepersTotemItem {
    override val itemName: String = "Щиток"
    override val model: Int = 12
    override val id: String = "depers_shield"
    init { this.createItem() }

    override fun onInventoryTick(player: Player) {
        if(!compare(player.inventory.itemInOffHand)) return
        val toolType = player.inventory.itemInMainHand.type.toString()
        when(toolType.split("_").last().uppercase()){
            "PICKAXE" ->
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.FAST_DIGGING,
                    40, 1)
                )
            "AXE" -> {
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.FAST_DIGGING,
                    40, 0)
                )
                player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0))
            }
            "SWORD" ->
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.INCREASE_DAMAGE,
                    40, 1)
                )
            "HOE" ->
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.INCREASE_DAMAGE,
                    40, 5)
                )
            "SHOVEL" ->
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.FAST_DIGGING,
                    40, 1)
                )
        }
    }

    @EventHandler
    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val hand = event.hand ?: return
        if(!compare(player.inventory.getItem(hand))) return

        val revivalTask = Runnable{
            player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 20*10, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 1, 2))
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*20, 0))
            player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 0))
            player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*40, 0))
        }

        RLEngineTaskManager.runTaskLater(revivalTask, 1L)

    }

}