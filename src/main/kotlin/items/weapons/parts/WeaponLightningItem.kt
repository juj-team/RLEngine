package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object WeaponLightningItem : WeaponPart {
    override val id: String = "weapon_part_lightning_rod"
    override val model = 44434
    override val name = Component.text("Громоотвод").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}