package items.depers

import util.RLEngineTaskManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.min
import kotlin.random.Random

object ShockerTotemItem: DepersTotemItem {
    override val itemName: String = "Шокер"
    override val model: Int = 6
    override val id: String = "depers_shocker"
    private val painMessages = listOf(
        "Сейчас будет больно.",
        "Сейчас будет крайне больно.",
        "Сейчас будет пиздец больно.",
        "Завтра это точно будет болеть..",
        "Ну всё, конец."
    )
    

    override fun onInventoryTick(player: Player) {
        val c = Random.nextDouble(0.0, 1.0)
        if(c > 0.005) return

        var count = 0
        for(item: ItemStack? in player.inventory.contents){
            item ?: continue
            if(compare(item)) count += 1
        }
        if(count < 1) return
        player.sendMessage(Component.text(painMessages[min(count-1, 4)]).color(TextColor.color(160, 160, 160)))
        val shockTask = Runnable{
            player.damage(4.5*count)
            player.world.playSound(player.location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f)
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SPEED,
                    20 * 20*count, 1*count)
            )
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.FAST_DIGGING,
                    20 * 20*count, 1*count)
            )
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.INCREASE_DAMAGE,
                    20 * 20*count, 1*count)
            )
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.DARKNESS,
                    20 * 3 *count, 3*count)
            )
        }
        RLEngineTaskManager.runTaskLater(shockTask, 20L)
    }

    override fun onPlayerResurrect(event: EntityResurrectEvent) {
        // do nothing
    }

}