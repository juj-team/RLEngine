package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object BayonetItem : WeaponPart {
    override val id: String = "weapon_part_bayonet"
    override val model = 44427
    override val name = Component.text("Штык").decoration(TextDecoration.ITALIC, false)


}