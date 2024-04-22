package items.misc

import ailments.MoonInfection
import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.concurrent.ThreadLocalRandom

    object MoondustItem: AbstractRLItem {
    override val baseItem: Material = Material.WHITE_DYE
    override val model: Int = 44401
    override val id: String = "moon_dust"
    private val effects = listOf(
        PotionEffect(PotionEffectType.NIGHT_VISION, 20*60*10, 0),
        PotionEffect(PotionEffectType.GLOWING, 20*60*10, 0),
        PotionEffect(PotionEffectType.SATURATION, 20*60*10, 0),
        PotionEffect(PotionEffectType.JUMP, 20*60*10, 0),
        PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*10, 1),
        PotionEffect(PotionEffectType.REGENERATION, 20*60*10, 0),
        PotionEffect(PotionEffectType.SPEED, 20*60*10, 1),
    )
    
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(Component.text("Лунная пыль"))

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    fun onUse(event: PlayerInteractEvent){
        if(event.isCancelled) return
        if(event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return
        val item = event.item ?: return
        if(!compare(item)) return

        item.amount -= 1
        event.player.addPotionEffects(effects)
        if(ThreadLocalRandom.current().nextDouble() < 0.25){
            MoonInfection.setStatus(true, event.player)
        }
    }
}