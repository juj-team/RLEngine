package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

    object WeaponNetItem : WeaponPart {
    override val id: String = "weapon_part_net_launcher"
    override val model = 44431
    override val name = Component.text("Сеть").decoration(TextDecoration.ITALIC, false)


}