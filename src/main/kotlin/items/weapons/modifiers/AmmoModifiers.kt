package items.weapons.modifiers

import org.bukkit.NamespacedKey

enum class AmmoModifiers(val key: NamespacedKey) {
    BIG_AMMO(NamespacedKey("rle", "big_ammo")),
    BONEBREAKER(NamespacedKey("rle", "bonebreaker")),
    POISONED(NamespacedKey("rle", "poisoned_ammo")),
    WITHERED(NamespacedKey("rle", "withered_ammo")),
    WEAKENING(NamespacedKey("rle", "weakening_ammo")),
}