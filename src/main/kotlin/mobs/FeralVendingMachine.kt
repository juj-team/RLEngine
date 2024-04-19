package mobs

import listeners.RLEngineListeners
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Warden
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.persistence.PersistentDataType

class FeralVendingMachine(
    override val loc: Location,
    private val warden: Warden = loc.world.spawn(loc, Warden::class.java)
): RLEngineEntity {

    override val id: String = "vending_machine"
    init {
        warden.customName(Component.text("vending_machine").decoration(TextDecoration.ITALIC, false))
        warden.isCustomNameVisible = false
        warden.removeWhenFarAway = true
        warden.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue?.div(2.0)
        warden.persistentDataContainer.set(
            NamespacedKey("rle", "entity_id"),
            PersistentDataType.STRING,
            id
        )

        RLEngineListeners.register(this)
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        if (event.isCancelled) return
        val cause = event.player.lastDamageCause
        if (cause is EntityDamageByEntityEvent) {
            val killer = cause.damager
            if (killer == warden) {
                val deathMessage = "${event.entity.name} был убит Вардогусом"
                event.deathMessage = deathMessage
            }
        }
    }

}