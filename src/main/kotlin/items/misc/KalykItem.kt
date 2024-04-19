package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.RLEngineTaskManager
import kotlin.random.Random

object KalykItem: AbstractRLItem {
    override val baseItem: Material = Material.PETRIFIED_OAK_SLAB
    override val model: Int = 0
    override val id: String = "hookah"
    val fuelKey = NamespacedKey("rle", "fuel")
    private val allowedFuels: Map<Material, Int> = mapOf(
        Material.APPLE to 1,
        Material.CARROT to 2,
        Material.GLOW_BERRIES to 3,
        Material.HONEYCOMB to 4
    )
    val fuels = mapOf(
        0 to listOf(
            PotionEffect(PotionEffectType.LUCK, 20*3, 0, true, false, false)
        ),
        1 to listOf(
            PotionEffect(PotionEffectType.REGENERATION, 20*3, 0, true, false, false),
            PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*3, 1, true, true, true),
            PotionEffect(PotionEffectType.HUNGER, 20*3, 0, true, true, true)
        ),
        2 to listOf(
            PotionEffect(PotionEffectType.REGENERATION, 20*3, 0, true, false, false),
            PotionEffect(PotionEffectType.ABSORPTION, 20*3, 1, true, true, true),
            PotionEffect(PotionEffectType.SLOW_DIGGING, 20*3, 0, true, true, true)
        ),
        3 to listOf(
            PotionEffect(PotionEffectType.REGENERATION, 20*3, 0, true, false, false),
            PotionEffect(PotionEffectType.FAST_DIGGING, 20*3, 1, true, true, true),
            PotionEffect(PotionEffectType.CONFUSION, 20*3, 0, true, true, true),
            PotionEffect(PotionEffectType.GLOWING, 20*3, 0, true, true, true)
        ),
        4 to listOf(
            PotionEffect(PotionEffectType.LUCK, 20*3, 0, true, false, false)
        ),
    )
    init {
        this.createItem()
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers().filter{
                val offhand = it.inventory.itemInOffHand
                offhand.type == baseItem
            }.forEach{ onInventoryTick(it) }
        }, 20L, 10L)
    }
    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.displayName(
            Component.text("мощный калик", TextColor.color(200,100,250))
                .decoration(TextDecoration.ITALIC, false)
        )

        resultMeta.lore(listOf(
            Component.text("кайфун надвигается", TextColor.color(200,200,200))
        ))
        resultMeta.persistentDataContainer.set(
            NamespacedKey("rle", "fuel"),
            PersistentDataType.INTEGER,
            0
        )
        result.setItemMeta(resultMeta)
        return result
    }

    fun onInventoryTick(player: Player){
        val fuelType = player.inventory.itemInOffHand.itemMeta.persistentDataContainer.get(
            fuelKey,
            PersistentDataType.INTEGER
        )
        if(fuelType == null){
            player.inventory.itemInOffHand.itemMeta.persistentDataContainer.set(
                fuelKey,
                PersistentDataType.INTEGER,
                0
            )
            return
        }
        player.addPotionEffects(fuels[fuelType] ?: return)
        for(i in 0..Random.nextInt(2, 5)){
            player.world.spawnParticle(
                Particle.EXPLOSION_NORMAL,
                player.location.x,
                player.location.y+0.2,
                player.location.z,
                0,
                Random.nextDouble(0.0, 0.1),
                Random.nextDouble(0.1, 0.2),
                Random.nextDouble(0.0, 0.1),
                0.1)
        }
    }
    @EventHandler
    fun onKalykRefill(event: PlayerInteractEvent){
        if(event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return
        if(!event.player.isSneaking) return
        val kalykItem = event.player.inventory.itemInOffHand
        val kalykFuel = event.player.inventory.itemInMainHand

        if(!kalykItem.hasItemMeta()) return
        if(kalykItem.type != Material.PETRIFIED_OAK_SLAB) return

        if(kalykFuel.type !in allowedFuels.keys) return

        val kalykMeta = kalykItem.itemMeta
        kalykMeta.persistentDataContainer.set(
            NamespacedKey("rle", "fuel"),
            PersistentDataType.INTEGER,
            allowedFuels[kalykFuel.type] ?: 0
        )
        kalykItem.setItemMeta(kalykMeta)
    }
}