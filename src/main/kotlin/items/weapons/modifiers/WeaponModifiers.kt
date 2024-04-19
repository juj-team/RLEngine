package items.weapons.modifiers

import org.bukkit.NamespacedKey

enum class WeaponModifiers(val key: NamespacedKey) {
    BAYONETTE(NamespacedKey("rle", "has_bayonet")),
    IGNITER(NamespacedKey("rle", "has_igniter")),
    HOOKED(NamespacedKey("rle", "has_hook")),
    GRENADES(NamespacedKey("rle", "has_grenades")),
    NETS(NamespacedKey("rle", "has_nets")),
    LIGHTNING(NamespacedKey("rle", "has_lightning")),
    FAST_LOAD(NamespacedKey("rle", "has_fast_load")),
}