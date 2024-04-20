package items.weapons

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Shulker
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

object WeaponTableSummonerItem: AbstractRLItem {
    override val baseItem: Material = Material.CHAIN_COMMAND_BLOCK
    override val model: Int = 44436
    override val id: String = "weapon_table"
    init{
        this.createItem()
    }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(Component.text("Оружейный стол"))
        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    fun onPlace(event: PlayerInteractEvent){
        if(event.action != Action.RIGHT_CLICK_BLOCK) return
        if(!event.isBlockInHand) return

        val tableItem = event.player.inventory.itemInMainHand
        if(!compare(tableItem)) return

        val block = event.clickedBlock ?: return
        val clickedFace = event.blockFace
        val spawnPos = block.location.add(clickedFace.direction)
        val shulker = spawnPos.world.spawnEntity(spawnPos, EntityType.SHULKER) as Shulker
        shulker.isInvulnerable = true
        shulker.setAI(false)
        shulker.customName(Component.text("gun"))
        shulker.isCustomNameVisible = false
        shulker.color = DyeColor.BLACK
        shulker.isSilent = true
        tableItem.amount -= 1
    }
}