package climate.calculation

import org.bukkit.Material
import org.bukkit.entity.Player

object EquipmentWarmness : TemperatureCalculator {
    private val armorWarmness: Map<Material, Double> =
        mapOf(
            // netherite
            Material.NETHERITE_HELMET to 2.0,
            Material.NETHERITE_CHESTPLATE to 5.0,
            Material.NETHERITE_LEGGINGS to 3.0,
            Material.NETHERITE_BOOTS to 2.0,
            // diamond
            Material.DIAMOND_HELMET to 0.0,
            Material.DIAMOND_CHESTPLATE to 1.0,
            Material.DIAMOND_LEGGINGS to 1.0,
            Material.DIAMOND_BOOTS to 0.0,
            // iron
            Material.IRON_HELMET to 0.0,
            Material.IRON_CHESTPLATE to 1.0,
            Material.IRON_LEGGINGS to 0.0,
            Material.IRON_BOOTS to 1.0,
            // gold
            Material.GOLDEN_HELMET to -2.0,
            Material.GOLDEN_CHESTPLATE to -4.0,
            Material.GOLDEN_LEGGINGS to -3.0,
            Material.GOLDEN_BOOTS to 0.0,
            // leather
            Material.LEATHER_HELMET to 8.0,
        )

    private val itemWarmness: Map<Material, Double> =
        mapOf(
            Material.HEART_OF_THE_SEA to -10.0,
            Material.SOUL_LANTERN to -5.0,
            Material.LANTERN to 3.0,
            Material.TORCH to 5.0,
            Material.LAVA_BUCKET to 10.0,
            Material.NETHER_STAR to 20.0,
        )

    private fun inLeather(player: Player): Boolean {
        return (
            player.equipment.chestplate?.type == Material.LEATHER_CHESTPLATE &&
                player.equipment.leggings?.type == Material.LEATHER_LEGGINGS &&
                player.equipment.boots?.type == Material.LEATHER_BOOTS
        )
    }

    private fun equippedItemWarm(player: Player): Double {
        val offHandItem = player.equipment.itemInOffHand.type
        val mainHandItem = player.equipment.itemInMainHand.type

        val offHandWarmness = itemWarmness[offHandItem] ?: 0.0
        val mainHandWarmness = itemWarmness[mainHandItem] ?: 0.0

        return offHandWarmness + mainHandWarmness
    }

    override fun calculate(player: Player): Double {
        var temperature = 0.0
        val equipment = player.equipment

        if (inLeather(player)) {
            temperature += 17.0
        }
        temperature += armorWarmness[equipment.helmet?.type] ?: 0.0
        temperature += armorWarmness[equipment.chestplate?.type] ?: 0.0
        temperature += armorWarmness[equipment.leggings?.type] ?: 0.0
        temperature += armorWarmness[equipment.boots?.type] ?: 0.0

        temperature += equippedItemWarm(player)

        return temperature
    }
}
