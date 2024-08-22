package items.extra

import items.AbstractRLItem
import items.depers.ShieldTotemItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.RLEngineTaskManager

object PowerGlovesItem : AbstractRLItem {
    override val baseItem = Material.DIAMOND_CHESTPLATE
    override val model = 1
    override val id = "power_gloves"

    private const val NAME = "Рабочие перчатки"

    init {
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers()
                .filter(::filterPlayer)
                .forEach(::onInventoryTick)
        }, 20L, 10L)
    }

    private fun filterPlayer(player: Player) : Boolean {
        val chestplate = player.inventory.chestplate

        return !(chestplate == null || !compare(chestplate))
    }

    private fun onInventoryTick(player: Player) {
        val offHand = player.inventory.itemInOffHand

        val strengthLevel = if (ShieldTotemItem.compare(offHand)) 3 else 1
        val effect = PotionEffect(PotionEffectType.FAST_DIGGING, 20, strengthLevel, false, false, false)

        player.addPotionEffects(listOf(effect))
    }

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)

        resultMeta.displayName(
            Component.text(NAME)
                .decoration(TextDecoration.ITALIC, false)
                .color(TextColor.color(180, 100, 9))
        )

        result.setItemMeta(resultMeta)

        return result
    }
}