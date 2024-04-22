package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object ShotgunMagItem : WeaponPart {
    override val id: String = "weapon_part_shotgun_mag"
    override val model = 44432
    override val name = Component.text("Магазин дробовика").decoration(TextDecoration.ITALIC, false)


}