package util

import RadioLampEngine
import climate.TemperatureCalculator
import listeners.depers.DepersGrowth
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

object RLEngineTaskManager {
    init{
        TemperatureCalculator
        DepersGrowth
    }
    fun runTaskLater(task: Runnable, delay: Long): BukkitTask {
        return Bukkit.getScheduler().runTaskLater(RadioLampEngine.instance, task, delay)
    }

    fun runTask(task: Runnable, delay: Long, period: Long): BukkitTask {
        return Bukkit.getScheduler().runTaskTimer(RadioLampEngine.instance, task, delay, period)
    }

}