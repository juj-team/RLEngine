package ailments

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

interface RLEngineAilment {
    val id: String
    val processor: Runnable
        get() = Runnable{
            Bukkit.getOnlinePlayers().forEach{
                if(getStatus(it)) processor(it)
            }
        }

    fun processor(player: Player)

    fun getStatus(player: Player): Boolean{
        val pdc = player.persistentDataContainer
        val result = pdc.get(NamespacedKey("rle", id), PersistentDataType.BOOLEAN)
        if(result == null){
            pdc.set(NamespacedKey("rle", id), PersistentDataType.BOOLEAN, false)
            return false
        }
        return result
    }

    fun setStatus(status: Boolean, player: Player){
        val pdc = player.persistentDataContainer
        pdc.set(NamespacedKey("rle", id), PersistentDataType.BOOLEAN, status)
    }
}