package ailments

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.concurrent.ThreadLocalRandom

object MoonInfection: RLEngineAilment {
    override val id: String = "moonfection"
    data class MoonEffect(val cooldown: Int, val potionEffects: List<PotionEffect>)
    private val cooldownKey = NamespacedKey("rle", "moonfectioncooldown") //int
    private val effects = listOf(
        MoonEffect(60 * 3, listOf(
            PotionEffect(PotionEffectType.LEVITATION, 20*30, 0),
            PotionEffect(PotionEffectType.GLOWING, 20*30, 0),
            PotionEffect(PotionEffectType.NIGHT_VISION, 20*30, 0),
        )),
        MoonEffect(60 * 7, listOf(
            PotionEffect(PotionEffectType.CONFUSION, 20*30, 0),
            PotionEffect(PotionEffectType.WEAKNESS, 20*30, 0)
        )),
        MoonEffect(60 * 5, listOf(
            PotionEffect(PotionEffectType.DARKNESS, 20*30, 0),
            PotionEffect(PotionEffectType.WEAKNESS, 20*30, 0)
        ))
    )
    init{
        RLEngineAilments.register(id, this)
    }
    override fun processor(player: Player) {
        val cooldown = getCooldown(player)
        if(cooldown > 0){
            setCooldown(player, cooldown - 1)
            return
        }

        when(val outcome = ThreadLocalRandom.current().nextInt(0,10)){
            0,1,3,5 -> {
                val effect = effects[outcome % effects.size]
                setCooldown(player, effect.cooldown)
                player.addPotionEffects(effect.potionEffects)
            }
            6,7 -> {
                player.sendMessage(
                    Component.text("Вы чувствуете удивительную лёгкость...", TextColor.color(160,160,180))
                        .decoration(TextDecoration.ITALIC, true)
                )
                setCooldown(player, 100)
            }
            8 -> {
                player.sendMessage(
                    Component.text("Вы чувствуете ужасное присутствие в этих пустынях...", TextColor.color(180,160,180))
                        .decoration(TextDecoration.ITALIC, true)
                )
                setCooldown(player, 180)
            }
        }
    }
    private fun setCooldown(player: Player, amount: Int){
        player.persistentDataContainer.set(
            cooldownKey,
            PersistentDataType.INTEGER,
            amount
        )
    }
    private fun getCooldown(player: Player): Int{
        val infectionCooldown = player.persistentDataContainer.get(
            cooldownKey,
            PersistentDataType.INTEGER
        )
        if(infectionCooldown == null) {
            player.persistentDataContainer.set(
                cooldownKey,
                PersistentDataType.INTEGER,
                0
            )
            return 0
        }
        return infectionCooldown
    }

}