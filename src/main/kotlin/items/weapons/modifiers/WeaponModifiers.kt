package items.weapons.modifiers

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

enum class WeaponModifiers(val key: NamespacedKey) {
    BAYONET(NamespacedKey("rle", "has_bayonet")),
    IGNITER(NamespacedKey("rle", "has_igniter")),
    HOOKED(NamespacedKey("rle", "has_hook")),
    GRENADES(NamespacedKey("rle", "has_grenades")),
    NETS(NamespacedKey("rle", "has_nets")),
    LIGHTNING(NamespacedKey("rle", "has_lightning")),
    ;
    companion object{
        fun hasModifier(weapon: ItemStack, mod: WeaponModifiers): Boolean{
            return weapon.itemMeta.persistentDataContainer.has(mod.key)
        }
    }
}