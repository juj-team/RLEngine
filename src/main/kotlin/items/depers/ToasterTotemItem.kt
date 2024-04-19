package items.depers

import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent

object ToasterTotemItem: DepersTotemItem {
    override val itemName: String = "Тостер"
    override val model: Int = 13
    override val id: String = "depers_toaster"
    init { this.createItem() }

    override fun onInventoryTick(player: Player) {
        // в пизду
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        // в пизду
    }
}