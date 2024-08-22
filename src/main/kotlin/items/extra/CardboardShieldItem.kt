package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object CardboardShieldItem : AbstractRLItem {
    override val baseItem = Material.SHIELD
    override val model = 1
    override val id = "cardboard_shield"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(Component.text("Картонный щит").decoration(TextDecoration.ITALIC, false))
        result.setItemMeta(resultMeta)

        return result
    }

    @EventHandler
    private fun onPunch(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return

        val inventory = player.inventory
        val shield: ItemStack = if (compare(inventory.itemInOffHand)) {
            inventory.itemInOffHand
        } else if (compare(inventory.itemInMainHand)) {
            inventory.itemInMainHand
        } else return

        if (event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) >= 0) return

        shield.damage(Int.MAX_VALUE, player)
    }
}