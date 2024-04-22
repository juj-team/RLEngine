package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object RifledBarrelItem: WeaponPart {
    override val id: String = "weapon_part_rifled_barrel"
    override val model = 44422
    override val name = Component.text("Нарезной ствол").decoration(TextDecoration.ITALIC, false)

    
}