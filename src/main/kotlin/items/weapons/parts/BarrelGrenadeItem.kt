package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object BarrelGrenadeItem : WeaponPart {
    override val id: String = "weapon_part_grenade_launcher"
    override val model = 44430
    override val name = Component.text("Подствольный гранатомёт").decoration(TextDecoration.ITALIC, false)
}