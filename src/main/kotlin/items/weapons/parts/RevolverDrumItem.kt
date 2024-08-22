package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object RevolverDrumItem : WeaponPart {
    override val id: String = "weapon_part_revolver_drum"
    override val model = 44408
    override val name = Component.text("Револьверный барабан").decoration(TextDecoration.ITALIC, false)
}