package mobs

import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.event.Listener
import org.bukkit.persistence.PersistentDataType

interface RLEngineEntity: Listener {
    val id: String
    val loc: Location
    val idKey: NamespacedKey
        get() = NamespacedKey("rle", "entity_id")

    fun compare(other: Entity): Boolean{
        if(!other.persistentDataContainer.has(idKey)) return false
        val otherId = other.persistentDataContainer.get(
            idKey, PersistentDataType.STRING
        )
        return otherId == id
    }
}