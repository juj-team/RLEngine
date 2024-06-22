package items.weapons.modifiers

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object ModifierListener: Listener {
    @EventHandler
    fun onBulletHit(event: ProjectileHitEvent){
        val projectile = event.entity
        val victim = event.hitEntity
        val hitBlock = event.hitBlock
        val shooter = projectile.shooter

        if(shooter !is Player) return

        val modifiers = WeaponModifiers.entries.filter {WeaponModifiers.hasModifier(projectile.persistentDataContainer, it)}
        modifiers.forEach { it.executor(shooter, victim, hitBlock) }
    }
}