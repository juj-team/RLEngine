package items.weapons.modifiers

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer

enum class AmmoModifiers(val key: NamespacedKey) {
    BIG_AMMO(NamespacedKey("rle", "big_ammo")),
    BONE_BREAKER(NamespacedKey("rle", "bone_breaker")),
    POISONED(NamespacedKey("rle", "poisoned_ammo")),
    WITHERED(NamespacedKey("rle", "withered_ammo")),
    WEAKENING(NamespacedKey("rle", "weakening_ammo")),
    ;
    companion object{
        fun hasModifier(ammoData: PersistentDataContainer, mod: AmmoModifiers): Boolean{
            return ammoData.has(mod.key)
        }
    }
}