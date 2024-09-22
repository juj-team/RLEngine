package items.weapons.parts

import items.weapons.modifiers.WeaponModifiers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

object BarrelGrenadeItem : WeaponPart {
    override val id: String = "weapon_part_grenade_launcher"
    override val model = 44430
    override val name = Component.text("Подствольный гранатомёт").decoration(TextDecoration.ITALIC, false)

    @EventHandler
    private fun onExplode(event: EntityDamageByEntityEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return
        val damager = event.damager as? Player ?: return

        val mainHandContainer = damager.inventory.itemInMainHand.itemMeta?.persistentDataContainer

        val barrelKey = WeaponModifiers.GRENADES.key
        if (mainHandContainer != null && !mainHandContainer.has(barrelKey)) return

        event.damage += 6
    }
}