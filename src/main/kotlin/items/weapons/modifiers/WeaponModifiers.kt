package items.weapons.modifiers

import gameclass.RLEngineGameClass
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
            val directionVector = shooter.location.toVector().subtract(victim.location.toVector()).normalize()
            victim.velocity.add(directionVector.multiply(5))
        } else if(block != null){
            val directionVector = block.location.toVector().subtract(shooter.location.toVector()).normalize()
            shooter.velocity.add(directionVector.multiply(5))
        }
    }),
    GRENADES(NamespacedKey("rle", "has_grenades"), {_, victim, block ->
        if(Random.nextInt(1..100) < 15) {
            if (victim != null) {
                victim.location.world.createExplosion(victim.location, 1.0f, false, true)
            } else block?.location?.world?.createExplosion(block.location, 1.0f, false, true)
        }
    }),
    NETS(NamespacedKey("rle", "has_nets"), { _, victim, _ ->
        if(Random.nextInt(1..100) < 30) {
            if (victim != null && victim is LivingEntity) {
                victim.addPotionEffects(listOf(
                    org.bukkit.potion.PotionEffect(PotionEffectType.SLOW, 7, 20*10)
                ))
            }
        }
    }),
    LIGHTNING(NamespacedKey("rle", "has_lightning"), {_, victim, block ->
        if(Random.nextInt(1..100) < 10) {
            if (victim != null) {
                victim.location.world.strikeLightning(victim.location)
            } else block?.location?.world?.strikeLightning(block.location)
        }
    }),
    BULDYGA(NamespacedKey("rle", "has_buldyga"), { shooter, victim, _ ->
        if(shooter is Player && RLEngineGameClass.getClass(shooter) == gameclass.RLEngineGameClass.MAGE && Random.nextInt(1..100) < 5){
            victim?.location?.world?.spawnEntity(victim.location, org.bukkit.entity.EntityType.VEX)
        }
    })
    ;
    companion object{
        fun hasModifier(weaponData: PersistentDataContainer, mod: WeaponModifiers): Boolean{
            return weaponData.has(mod.key)
        }
    }
}