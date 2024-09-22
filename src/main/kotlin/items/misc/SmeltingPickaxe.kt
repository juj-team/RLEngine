package items.misc

import RadioLampEngine
import com.sk89q.worldguard.protection.flags.Flags
import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import util.BlockUtils


data class SmeltResult(val result: Collection<ItemStack>, val smelted: Boolean)


object SmeltingPickaxe : AbstractRLItem {
    override val baseItem: Material = Material.DIAMOND_PICKAXE
    override val model: Int = 44404
    override  val id: String = "smelting_pickaxe"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val orange = TextColor.color(180, 100, 0)

        resultMeta.displayName(Component.text("Кирка с печью", orange))
        resultMeta.lore(
            listOf(
                Component.text("Плавит на ходу", orange)
                    .decoration(TextDecoration.ITALIC, true)
            )
        )

        resultMeta.addEnchant(Enchantment.DIG_SPEED, 5, true)
        resultMeta.addEnchant(Enchantment.DURABILITY, 3, true)
        resultMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true)
        resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        resultMeta.setCustomModelData(model)
        result.setItemMeta(resultMeta)

        return result
    }

    private fun smeltItems(items: Collection<ItemStack>) : SmeltResult {
        var anythingSmelted = false
        val smeltedItems = mutableListOf<ItemStack>()

        for (item in items) {
            var smelted = false

            for (recipe in Bukkit.recipeIterator()) {
                if (recipe !is FurnaceRecipe) continue
                if (!recipe.inputChoice.test(item)) continue

                val result = recipe.result
                result.amount = item.amount

                smeltedItems.add(result)
                smelted = true
                anythingSmelted = true
                break
            }

            if (!smelted) smeltedItems.add(item)
        }

        return SmeltResult(smeltedItems, anythingSmelted)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val activeItem = event.player.inventory.itemInMainHand
        if (!compare(activeItem)) return

        if (!BlockUtils.playerCan(event.player, event.block.location, Flags.BLOCK_BREAK)) return

        val plugin = RadioLampEngine.instance as RadioLampEngine
        val cp = plugin.coreProtectAPI

        cp?.logRemoval(
            event.player.name,
            event.block.location,
            event.block.type,
            event.block.blockData,
        )

        val drops = event.block.getDrops(activeItem)
        val (resultItems, canSmeltAnything) = smeltItems(drops)

        val blockCentre = event.block.location.toCenterLocation()
        if (canSmeltAnything) {
            event.block.world.playSound(
                blockCentre,
                Sound.BLOCK_FIRE_AMBIENT,
                1.0f,
                1.0f,
            )
            event.block.world.spawnParticle(
                Particle.LAVA,
                blockCentre,
                10,
            )
        }

        event.block.type = Material.AIR

        for (drop in resultItems) event.block.world.dropItem(blockCentre, drop)

        activeItem.damage(1, event.player)

        event.block.breakNaturally(activeItem)

        event.isCancelled = true
    }
}