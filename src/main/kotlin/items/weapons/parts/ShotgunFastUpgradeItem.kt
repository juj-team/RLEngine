package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object ShotgunFastUpgradeItem : WeaponPart {
    override val id: String = "weapon_part_fast_shotgun"
    override val model = 44433
    override val name = Component.text("Быстрый затвор дробовика").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}