package listeners.weapons

import gui.WeaponTableGui
import items.weapons.WeaponTableSummonerItem
import net.kyori.adventure.text.Component
import org.bukkit.DyeColor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Shulker
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

object WeaponTableInteractor: Listener {
    @EventHandler
    fun onTableClick(event: PlayerInteractAtEntityEvent){
        val table = event.rightClicked
        if(event.isCancelled || table.type != EntityType.SHULKER || table !is Shulker) return
        if(table.customName() != Component.text("gun") || table.color != DyeColor.BLACK) return
        if(event.player.isSneaking){
            table.world.dropItemNaturally(table.location, WeaponTableSummonerItem.getItem())
            table.remove()
            return
        }
        // a very lame check to see if player already has inventory open
        if(!event.player.openInventory.topInventory.toString().contains("CraftInventoryCrafting")) return
        WeaponTableGui(table).gui.show(event.player)
    }
}