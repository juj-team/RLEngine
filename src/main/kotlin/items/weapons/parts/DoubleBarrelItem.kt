package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object DoubleBarrelItem : WeaponPart {
    override val id: String = "weapon_part_double_barrel"
    override val model = 44425
    override val name = Component.text("Двойной ствол").decoration(TextDecoration.ITALIC, false)


}