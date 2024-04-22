package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

object HookItem : WeaponPart {
    override val id: String = "weapon_part_hook"
    override val model = 44429
    override val name = Component.text("Крюк").decoration(TextDecoration.ITALIC, false)


}