package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object BuldygaItem : WeaponPart {
    override val id: String = "weapon_part_buldyga"
    override val model = 44435
    override val name = Component.text("Булдыга").decoration(TextDecoration.ITALIC, false)


}