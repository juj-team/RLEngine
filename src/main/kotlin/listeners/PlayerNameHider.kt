package listeners

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.Team

object PlayerNameHider: Listener {
    val allowedTeamNames = listOf(
        "HideName",
        "jujplus",
        "miners",
    )

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
        allowedTeamNames.forEach {
            var team = scoreboard.getTeam(it)
            if(team == null){
                team = scoreboard.registerNewTeam(it)
                team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
            }
            if(team.hasPlayer(event.player)) return

        }
        scoreboard.getTeam("HideName")?.addPlayer(event.player)
    }
}