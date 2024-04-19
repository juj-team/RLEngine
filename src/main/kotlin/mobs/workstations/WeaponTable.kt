package mobs.workstations

import mobs.RLEngineEntity
import net.kyori.adventure.text.Component
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Shulker
import org.bukkit.persistence.PersistentDataType

class WeaponTable(override val id: String = "weapon_table", override val loc: Location) : RLEngineEntity {
    init{
        val shulker = loc.world.spawnEntity(
            loc,
            EntityType.SHULKER
        ) as Shulker
        shulker.color = DyeColor.BLACK
        shulker.customName(Component.text("gun"))
        shulker.persistentDataContainer.set(idKey, PersistentDataType.STRING, id)
    }

    
}