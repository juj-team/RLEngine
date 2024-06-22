package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Lightable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.RLEngineTaskManager

object GliderItem: AbstractRLItem {
    override val baseItem: Material = Material.IRON_CHESTPLATE
    override val model: Int = 44301
    override val id: String = "glider"
    init{
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers().filter{
                val chestPiece = it.inventory.chestplate
                chestPiece != null && compare(chestPiece)
            }.forEach{ onInventoryTick(it)}
        }, 2L, 10L)
    }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)

        resultMeta.displayName(Component.text("Параглайдер", TextColor.color(250,250,250)))
        resultMeta.lore(listOf(
            Component.text("летает", TextColor.color(250,250,250))
        ))
        result.setItemMeta(resultMeta)
        return result
    }

    fun onInventoryTick(player: Player){
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 30, 0, false, false, false))

        val blockUnderFeet = player.location.block.getRelative(BlockFace.DOWN)
        if(blockUnderFeet.type != Material.CAMPFIRE) return
        if(blockUnderFeet.blockData !is Lightable) return
        if(!(blockUnderFeet.blockData as Lightable).isLit) return
        player.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20*3, 17, false, false, false))
    }
}