package recipes

import items.weapons.guns.*
import items.weapons.modifiers.AmmoModifiers
import items.weapons.modifiers.WeaponModifiers
import items.weapons.parts.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class WeaponTableRecipes(
    val base: (item: ItemStack) -> Boolean,
    val modifier: (item: ItemStack) -> Boolean,
    val metal: (item: ItemStack) -> Boolean,
    val fuel: (item: ItemStack) -> Boolean = {item -> item.type == Material.BLAZE_ROD},
    val result: (weapon: ItemStack, modifier: ItemStack, metal: ItemStack, fuel: ItemStack) -> ItemStack
) {
    // base weapon creation
    LIGHT_RIFLE(
        base = {item -> item.type == Material.BOW},
        modifier = {item -> RifledBarrelItem.compare(item) },
        metal = {item -> item.amount >= 6 && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            LightRifleGunItem.getItem() }
    ),
    COLLAPSE_RIFLE(
        base = {item -> LightRifleGunItem.compare(item)},
        metal = {item -> item.amount >= 32
                && item.type == org.bukkit.Material.IRON_INGOT},
        modifier = {item -> ReturnSpringItem.compare(item)},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 32
            fuel.amount -= 1
            CollapseRifleGunItem.getItem() },
    ),
    HEAVY_RIFLE(
        base = {item -> item.type == Material.BOW},
        modifier = {item -> SmoothBarrelItem.compare(item) },
        metal = {item -> item.amount >= 18
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 18
            fuel.amount -= 1
            HeavyRifleWeaponItem.getItem() }
    ),
    ROCKET_GUN(
        base = {item -> HeavyRifleWeaponItem.compare(item)},
        modifier = {item ->
            item.type == Material.NETHERITE_SCRAP },
        metal = {item -> item.amount >= 64
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 64
            fuel.amount -= 1
            RocketGunItem.getItem() }
    ),
    REVOLVER(
        base = {item -> item.type == Material.CROSSBOW},
        modifier = {item ->
            FileInstrumentItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            RevolverGunItem.getItem() }
    ),
    SHOTGUN(
        base = {item -> RevolverGunItem.compare(item)},
        modifier = {item ->
            DoubleBarrelItem.compare(item) },
        metal = {item -> item.amount >= 8
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 8
            fuel.amount -= 1
            ShotgunGunItem.getItem() }
    ),
    SAWED_OFF(
        base = {item -> ShotgunGunItem.compare(item)},
        modifier = {item ->
            FileInstrumentItem.compare(item) },
        metal = {item -> item.amount >= 16
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 16
            fuel.amount -= 1
            SawedOffGunItem.getItem() }
    ),
    MACHINE_GUN(
        base = {item -> RevolverGunItem.compare(item)},
        modifier = {item ->
            SmoothBarrelItem.compare(item) },
        metal = {item -> item.amount >= 48
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 48
            fuel.amount -= 1
            MachineGunItem.getItem() }
    ),
    // weapon upgrading
    BAYONET_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                HeavyRifleWeaponItem,
                CollapseRifleGunItem,
                LightRifleGunItem,
                RevolverGunItem,
                SawedOffGunItem,
                ShotgunGunItem
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
               },
        modifier = {item ->
            BayonetItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.BAYONETTE.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    IGNITER_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                CollapseRifleGunItem,
                LightRifleGunItem,
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
        },
        modifier = {item ->
            IgniterItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.IGNITER.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    HOOK_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                CollapseRifleGunItem,
                LightRifleGunItem,
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
        },
        modifier = {item ->
            HookItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.HOOKED.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    GRENADES_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                CollapseRifleGunItem,
                LightRifleGunItem,
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
        },
        modifier = {item ->
            BarrelGrenadeItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.GRENADES.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    NETS_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                CollapseRifleGunItem,
                LightRifleGunItem,
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
        },
        modifier = {item ->
            WeaponNetItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.NETS.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    LIGHTNING_UPGRADE(
        base = {item ->
            val gunsWhitelist = listOf(
                CollapseRifleGunItem,
                LightRifleGunItem,
            )
            // comparison code
            var result = false
            gunsWhitelist.forEach{if(it.compare(item)) result = true; return@forEach}
            result
        },
        modifier = {item ->
            WeaponLightningItem.compare(item) },
        metal = {item -> item.amount >= 6
                && item.type == Material.IRON_INGOT},
        result = {weapon, modifier, metal, fuel ->
            val modKey = WeaponModifiers.LIGHTNING.key
            val result = weapon.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            weapon.amount -= 1
            modifier.amount -= 1
            metal.amount -= 6
            fuel.amount -= 1
            result
        }
    ),
    // ammo crafting
    HEAVY_AMMO(
        base = {item -> item.type == Material.ARROW && item.amount >= 16},
        metal = {item -> item.type == Material.IRON_INGOT && item.amount >= 32},
        modifier = {item -> item.type == Material.COPPER_INGOT && item.amount >= 56},
        result = {base, modifier, metal, fuel ->
            val modKey = AmmoModifiers.BIG_AMMO.key
            val result = base.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            resultMeta.displayName(Component.text("Крупнокалиберный патрон", TextColor.color(150,150,150)))
            result.setItemMeta(resultMeta)
            result.amount = 16

            base.amount -= 16
            modifier.amount -= 56
            metal.amount -= 32
            fuel.amount -= 1
            result
        }
    ),
    BONEBREAKER_AMMO(
        base = {item -> item.type == Material.ARROW && item.amount >= 16},
        metal = {item -> item.type == Material.IRON_INGOT && item.amount >= 10},
        modifier = {item -> item.type == Material.COPPER_INGOT && item.amount >= 14},
        result = {base, modifier, metal, fuel ->
            val modKey = AmmoModifiers.BONEBREAKER.key
            val result = base.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            resultMeta.displayName(Component.text("Патрон-костолом", TextColor.color(150,150,150)))
            result.setItemMeta(resultMeta)
            result.amount = 16

            base.amount -= 16
            modifier.amount -= 14
            metal.amount -= 10
            fuel.amount -= 1
            result
        }
    ),
    POISONED_AMMO(
        base = {item -> item.type == Material.ARROW && item.amount >= 16},
        metal = {item -> item.type == Material.FERMENTED_SPIDER_EYE},
        modifier = {item -> item.type == Material.GLASS},
        result = {base, modifier, metal, fuel ->
            val modKey = AmmoModifiers.POISONED.key
            val result = base.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            resultMeta.displayName(Component.text("Отравляющий патрон", TextColor.color(40,80,0)))
            result.setItemMeta(resultMeta)
            result.amount = 16

            base.amount -= 16
            modifier.amount -= 1
            metal.amount -= 1
            fuel.amount -= 1
            result
        }
    ),
    WITHERING_AMMO(
        base = {item -> item.type == Material.ARROW && item.amount >= 16},
        metal = {
            item -> item.type == Material.COPPER_INGOT},
        modifier = {
            item -> item.type == Material.WITHER_ROSE},
        result = {base, modifier, metal, fuel ->
            val modKey = AmmoModifiers.WITHERED.key
            val result = base.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            result.setItemMeta(resultMeta)
            result.amount = 16
            resultMeta.displayName(Component.text("Иссушающий патрон", TextColor.color(20,16,16)))

            base.amount -= 16
            modifier.amount -= 1
            metal.amount -= 1
            fuel.amount -= 1
            result
        }
    ),
    WEAKENING_AMMO(
        base = {item -> item.type == Material.ARROW && item.amount >= 16},
        metal = {
                item -> item.type == Material.COPPER_INGOT},
        modifier = {
                item -> item.type == Material.HONEYCOMB},
        result = {base, modifier, metal, fuel ->
            val modKey = AmmoModifiers.WEAKENING.key
            val result = base.clone()
            val resultMeta = result.itemMeta
            resultMeta.persistentDataContainer.set(
                modKey,
                org.bukkit.persistence.PersistentDataType.BOOLEAN,
                true
            )
            resultMeta.displayName(Component.text("Ослабляющий патрон", TextColor.color(79,1,6)))
            result.setItemMeta(resultMeta)
            result.amount = 16

            base.amount -= 16
            modifier.amount -= 1
            metal.amount -= 1
            fuel.amount -= 1
            result
        }
    ),
    ;
    companion object{
        fun getRecipeByIngredients(weapon: ItemStack, metal: ItemStack, modifier: ItemStack, fuel: ItemStack): ItemStack? {
            val targetRecipe = WeaponTableRecipes.entries
                .filter{it.base(weapon)}
                .filter{it.metal(metal)}
                .filter{it.fuel(fuel)}
                .firstOrNull { it.modifier(modifier) } ?: return null
            return targetRecipe.result(weapon, modifier, metal, fuel)
        }
    }
}
