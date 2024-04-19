package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object SmoothBarrelItem : WeaponPart {
    override val id: String = "weapon_part_smooth_barrel"
    override val model = 44423
    override val name = Component.text("Гладкий ствол").decoration(TextDecoration.ITALIC, false)

    init {
        this.createItem()
    }
}