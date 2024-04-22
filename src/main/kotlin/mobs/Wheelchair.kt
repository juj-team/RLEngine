package mobs

import io.papermc.paper.event.entity.EntityMoveEvent
import listeners.RLEngineListeners
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.Pig
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityEnterLoveModeEvent
import org.bukkit.persistence.PersistentDataType

class Wheelchair(
    override val loc: Location,
    private val pig: Pig = loc.world.spawnEntity(loc, EntityType.PIG) as Pig,
) : RLEngineEntity {
    override val id: String = "wheelchair"

    init{
        pig.isSilent = true
        pig.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)?.baseValue = 0.0
        pig.customName(Component.text("test"))

        pig.isPersistent = true
        pig.persistentDataContainer.set(
            NamespacedKey("rle", "entity_id"),
            PersistentDataType.STRING,
            id
        )
        RLEngineListeners.register(this)
    }
    // prevent from wandering
    // object persistence
    @EventHandler
    fun paralyseWheelchair(event: EntityMoveEvent){
        if(event.entity == pig && pig.passengers.isEmpty()) event.isCancelled = true
    }
    @EventHandler
    fun paralyseWheelchair(event: EntityEnterLoveModeEvent){
        if(event.entity == pig) event.isCancelled = true
    }
}
