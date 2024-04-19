package util

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

object LivingEntitiesInRadius {
    fun get(player: Player, radius: Double) : ArrayList<LivingEntity>{
        val res = ArrayList<LivingEntity>()
        for(mob in player.world.getNearbyEntities(player.location, radius, radius, radius)){
            if(mob !is LivingEntity || mob == player) continue
            res.add(mob)
        }
        return res
    }
}