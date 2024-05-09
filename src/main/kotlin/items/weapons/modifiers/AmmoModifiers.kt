package items.weapons.modifiers

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

enum class AmmoModifiers(val key: NamespacedKey) {
    BIG_AMMO(NamespacedKey("rle", "big_ammo")),
    BONE_BREAKER(NamespacedKey("rle", "bone_breaker")),
    POISONED(NamespacedKey("rle", "poisoned_ammo")),
    WITHERED(NamespacedKey("rle", "withered_ammo")),
    WEAKENING(NamespacedKey("rle", "weakening_ammo")),
    ;
    companion object{
        fun hasModifier(ammo: ItemStack, mod: AmmoModifiers): Boolean{
            return ammo.itemMeta.persistentDataContainer.has(mod.key)
        }
    }
}