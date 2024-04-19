package items.misc

import items.AbstractRLItem
import util.RLEngineTaskManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object VeryHotPhoneItem: AbstractRLItem {
    override val baseItem: Material = Material.TOTEM_OF_UNDYING
    override val model = 44302
    override val id = "very_hot_line"
    init{ this.createItem() }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("КРАЙНЕ ГОРЯЧАЯ ЛИНИЯ", TextColor.color(150,250,250)))
        resultMeta.lore(listOf(
            Component.text("жжется", TextColor.color(250,250,250))
        ))
        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    fun onEntityResurrect(event: EntityResurrectEvent){
        if(event.isCancelled || event.entity !is Player) return
        val player = event.entity as Player
        val usedItem = player.inventory.getItem(event.hand ?: return)
        if(!compare(usedItem)) return

        val successTask = {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE)
            player.removePotionEffect(PotionEffectType.SPEED)
            player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*180, 0))
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20*180, 0))
            player.fireTicks = 20*180
        }

        RLEngineTaskManager.runTaskLater(successTask, 1L)
    }
}