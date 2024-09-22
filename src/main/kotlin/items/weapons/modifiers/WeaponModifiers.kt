package items.weapons.modifiers

import gameclass.RLEngineGameClass
import items.weapons.parts.BuldygaItem
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random
import kotlin.random.nextInt

enum class WeaponModifiers(val key: NamespacedKey, val executor: (shooter: Entity, victim: Entity?, block: Block?) -> Unit) {
    BAYONET(NamespacedKey("rle", "has_bayonet"), {_, _, _ ->}),
    IGNITER(NamespacedKey("rle", "has_igniter"),{_, victim, _ ->
        if(victim != null){
            victim.fireTicks += 200
        }
    }),
    HOOKED(NamespacedKey("rle", "has_hook"), {shooter, victim, block ->
        if(victim != null){
            val shooterLocation = shooter.location.toVector()
            val victimLocation = victim.location.toVector()
            var directionVector = shooterLocation.clone().subtract(victimLocation)
            directionVector = directionVector.normalize()
            directionVector = directionVector.multiply(4)
            println(directionVector)
            victim.velocity = directionVector

        } else if(block != null){
            val directionVector = block.location.toVector().subtract(shooter.location.toVector()).normalize()
            shooter.velocity.add(directionVector.multiply(5))
        }
    }),
    GRENADES(NamespacedKey("rle", "has_grenades"), {shooter, victim, block ->
        if(Random.nextInt(1..100) < 15) {
            val location = victim?.location ?: block?.location

            location?.world?.createExplosion(location, 1.0f, false, true, shooter)
        }
    }),
    NETS(NamespacedKey("rle", "has_nets"), { _, victim, _ ->
        if(Random.nextInt(1..100) < 50) {
            if (victim != null && victim is LivingEntity) {
                victim.addPotionEffects(listOf(
                    org.bukkit.potion.PotionEffect(PotionEffectType.SLOW, 7 * 20, 20*10)
                ))
            }
        }
    }),
    LIGHTNING(NamespacedKey("rle", "has_lightning"), {_, victim, block ->
        if(Random.nextInt(1..100) < 20) {
            if (victim != null) {
                victim.location.world.strikeLightning(victim.location)
            } else block?.location?.world?.strikeLightning(block.location)
        }
    }),
    BULDYGA(NamespacedKey("rle", "has_buldyga"), { shooter, victim, _ ->
        if(shooter is Player && RLEngineGameClass.getClass(shooter) == gameclass.RLEngineGameClass.MAGE && Random.nextInt(1..100) < 80) {
            if (victim != null && victim is LivingEntity) {
                BuldygaItem.spawnVex(shooter, victim)
            }
        }
    }),
    SHOTGUN_MAG(NamespacedKey("rle", "has_shotgun_mag"), {_, _, _ -> }),
    ;
    companion object{
        fun hasModifier(weaponData: PersistentDataContainer, mod: WeaponModifiers): Boolean{
            return weaponData.has(mod.key)
        }
    }
}