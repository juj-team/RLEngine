package commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import util.RLEngineTaskManager

class ClassDialogCommand: CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>?,
    ): Boolean {
        if (sender !is Player) return true
        if (sender.persistentDataContainer.get(
                NamespacedKey("rle", "locked"),
                PersistentDataType.BOOLEAN,
            ) != true
        ) {
            return true
        }
        if (args.isNullOrEmpty()) return true

        when (args[0]) {
            "1" -> {
                sender.sendMessage(
                    Component.text(
                        "Тут даже удобно, но встаёт пара вопросов. " +
                                "Где ты понятно. Ты в кусте. " +
                                "А куст в Июле. Кажется, в Апельсиновом саду. " +
                                "Следующий вопрос. Кто ты?",
                    ).appendNewline().append(
                        Component.text("[Кто ты?]", TextColor.color(200, 100, 0))
                            .clickEvent(ClickEvent.runCommand("/classdialog 2")),
                    ),
                )
            }

            "2" -> {
                sender.sendMessage(
                    Component.text(
                        "Детали твоей личности всплывают в памяти и снова собираются плотным комком. " +
                                "Ты приподнимаешься и полусадишься в своём кусте. " +
                                "Такое чувство, будто этот мистический опыт (напоминает старую мистерию апельсинового культа, " +
                                "традиционно проходящую в сортире Мэрии) что-то поменял глубоко в тебе.",
                    ).appendNewline().append(
                        Component.text("Пора стать другим человеком. Выбери свою судьбу.")
                            .decoration(TextDecoration.BOLD, true),
                    )
                        .appendNewline().append(
                            Component.text(
                                "[Я не хочу ничего выбирать...]",
                                TextColor.color(200, 100, 0),
                            ).clickEvent(ClickEvent.runCommand("/classdialog 3 citizen")),
                        ).appendNewline().append(
                            Component.text(
                                "[Покорим этот мир силой металла!]",
                                TextColor.color(200, 100, 0),
                            ).clickEvent(ClickEvent.runCommand("/classdialog 3 engineer")),
                        ).appendNewline().append(
                            Component.text(
                                "[Покорим этот мир силой мистики!]",
                                TextColor.color(200, 100, 0),
                            ).clickEvent(ClickEvent.runCommand("/classdialog 3 mage")),
                        ).appendNewline().append(
                            Component.text(
                                "[как же я люблю убивать]",
                                TextColor.color(200, 100, 0),
                            ).clickEvent(ClickEvent.runCommand("/classdialog 3 headhunter")),
                        ).appendNewline().append(
                            Component.text(
                                "[Я стану человеком, который продал мир]",
                                TextColor.color(200, 100, 0),
                            ).clickEvent(ClickEvent.runCommand("/classdialog 3 businessman")),
                        ),
                )
            }

            "3" -> {
                sender.sendMessage(Component.text("Да. Отличный выбор. Доброе утро, Июль."))
                RLEngineTaskManager.runTaskLater(
                    {
                        setClass(sender, args[1])
                    }, 40L
                )
            }

            else -> return true
        }
        return true
    }

    private fun setClass(
        player: Player,
        choice: String?,
    ) {
        choice ?: return

        player.persistentDataContainer.set(
            NamespacedKey("rle", "locked"),
            PersistentDataType.BOOLEAN,
            false,
        )
        player.persistentDataContainer.set(
            NamespacedKey("jujclasses", "playerclass"),
            PersistentDataType.STRING,
            choice,
        )
        for (effect in player.activePotionEffects) player.removePotionEffect(effect.type)

        player.teleport(Location(player.world, -1420.5, 67.0, 6.0))
    }
}