package listeners

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.Team

object PlayerNameHider: Listener {

    // Load name teams from file. If there are none - revert to defaults.
    private const val DEFAULT_TEAM_NAME: String = "HideName"
    private const val PREMIUM_TEAM_NAME: String = "jujplus"

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val scoreboard = Bukkit.getScoreboardManager().mainScoreboard

        var hidingTeam = scoreboard.getTeam(DEFAULT_TEAM_NAME)
        var premiumTeam = scoreboard.getTeam(PREMIUM_TEAM_NAME)

        if (hidingTeam == null){
            hidingTeam = scoreboard.registerNewTeam(DEFAULT_TEAM_NAME)
            hidingTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
        }
        if (premiumTeam == null){
            premiumTeam = scoreboard.registerNewTeam(PREMIUM_TEAM_NAME)
            hidingTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
        }

        if(premiumTeam.hasPlayer(event.player)) return
        if(!hidingTeam.hasPlayer(event.player)) hidingTeam.addPlayer(event.player)
    }
}