package items.extra

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.RLEngineTaskManager

object NightGogglesItem : AbstractRLItem {
    override val baseItem = Material.DIAMOND_HELMET
    override val model = 1
    override val id = "night_goggles"
    private const val NAME = "Очки ночного зрения"
    private val activeNamespaceKey = NamespacedKey("rle", "is_active")

    init {
        RLEngineTaskManager.runTask({
            Bukkit.getOnlinePlayers()
                .filter(::filterPlayer)
                .forEach(::onInventoryTick)
        }, 20L, 10L)
    }

    private fun filterPlayer(player: Player) : Boolean {
        val helmet = player.inventory.helmet

        if (helmet == null || !compare(helmet)) return false

        return getActive(helmet)
    }

    fun setActive(item: ItemStack, value: Boolean) {
        val itemMeta = item.itemMeta
        val pdc = itemMeta.persistentDataContainer

        pdc.set(
            activeNamespaceKey,
            PersistentDataType.BOOLEAN,
            value,
        )

        val textStatus = if (value) "вкл." else "выкл."
        itemMeta.displayName(
            Component.text("$NAME [$textStatus]")
                .decoration(TextDecoration.ITALIC, false)
                .color(TextColor.color(180, 100, 9))
        )
        item.setItemMeta(itemMeta)
    }

    private fun getActive(item: ItemStack) : Boolean {
        if (!item.itemMeta.persistentDataContainer.has(activeNamespaceKey)) {
            setActive(item, true)
        }

        return item.itemMeta.persistentDataContainer.get(
            activeNamespaceKey,
            PersistentDataType.BOOLEAN,
        )!!
    }

    private fun switchActive(item: ItemStack) {
        setActive(item, !getActive(item))
    }

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)

        resultMeta.lore(
            listOf(
                Component.text("ВИДЕТЬ В ТЕМНОТЕ ЭТО ОЧЕНЬ КРУТО!!!")
            )
        )

        result.setItemMeta(resultMeta)

        setActive(result, true)

        return result
    }

    private fun onInventoryTick(player: Player) {
        player.addPotionEffects(
            listOf(
                PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 15, 0, false, false, false),
            )
        )
    }

    private fun playSwitchSound(player: Player) {
        player.world.playSound(
            player.location,
            Sound.BLOCK_LEVER_CLICK,
            SoundCategory.PLAYERS,
            0.5f,
            2.0f,
        )
    }

    @EventHandler
    private fun onSwitch(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return
        if (!event.player.isSneaking) return

        val inventory = event.player.inventory
        val helmet = inventory.helmet

        if (helmet == null || !compare(helmet)) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        if (inventory.itemInMainHand.type != Material.AIR) return

        val clickedBlock = event.clickedBlock
        if (clickedBlock != null && clickedBlock.type.isInteractable) return

        switchActive(helmet)

        playSwitchSound(event.player)

        val nowActive = getActive(helmet)
        if (!nowActive) event.player.removePotionEffect(PotionEffectType.NIGHT_VISION)

        val gogglesStatus = if (nowActive) "включены" else "выключены"
        event.player.sendActionBar(
            Component.text("Очки теперь $gogglesStatus")
                .color(TextColor.color(19, 188, 74))
        )

        event.isCancelled = true
    }
}