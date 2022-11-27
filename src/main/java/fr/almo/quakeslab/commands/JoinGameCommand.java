package fr.almo.quakeslab.commands;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import fr.almo.quakeslab.manager.PlayersOnGame;
import fr.almo.quakeslab.utilitys.AutoStartTimer;
import fr.almo.quakeslab.utilitys.Colorize;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Random;

public class JoinGameCommand implements CommandExecutor {
//
    private final QuakeSlab plugin;

    public JoinGameCommand (QuakeSlab plugin){
        this.plugin = plugin;
    }

//
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            //
            if(plugin.isPhase(Phases.WAITING) && PlayersOnGame.getPlayers().size() <= 2){
                if(!PlayersOnGame.getPlayers().contains(p)){
                    PlayersOnGame.getPlayers().add(p);

                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(ChatColor.YELLOW + "Vous avez rejoins la file d'attente du QuakeSlab !");


                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    final Scoreboard board = manager.getNewScoreboard();

                    //RED TEAM
                    Team redteam = board.registerNewTeam("Rouge");
                    redteam.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "ROUGE");
                    redteam.setPrefix(ChatColor.WHITE + "[" + ChatColor.RED + "Rouge" + ChatColor.WHITE + "] ");
                    redteam.setCanSeeFriendlyInvisibles(true);

                    //BLUE TEAM
                    Team blueteam = board.registerNewTeam("Bleu");
                    blueteam.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "BLEU");
                    blueteam.setPrefix(ChatColor.WHITE + "[" + ChatColor.RED + "Rouge" + ChatColor.WHITE + "] ");
                    blueteam.setCanSeeFriendlyInvisibles(true);

                    //RANDOM PICK
                    Random random = new Random();

                    if (random.nextBoolean() == true) {
                        redteam.addEntry(p.getName());
                    } else {
                        blueteam.addEntry(p.getName());
                    }

                    //SCOREBOARD
                    final Objective objective = board.registerNewObjective("test", "dummy");
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    objective.setDisplayName(ChatColor.YELLOW + "Quake" + ChatColor.BOLD + "SLAB");

                    Score score1 = objective.getScore(" ");
                    score1.setScore(10);

                    Score score2 = objective.getScore(ChatColor.WHITE + "Pseudo" + ChatColor.GRAY + ": " + ChatColor.BOLD + p.getDisplayName());
                    score2.setScore(9);

                    Score score3 = objective.getScore(ChatColor.WHITE + "Team" + ChatColor.GRAY + ": " + board.getEntryTeam(p.getName()).getDisplayName());
                    score3.setScore(8);

                    Score score4 = objective.getScore(ChatColor.WHITE + "Kills" + ChatColor.GRAY + ": ");
                    score4.setScore(7);

                    Score score5 = objective.getScore(ChatColor.WHITE + "Deaths" + ChatColor.GRAY + ": ");
                    score5.setScore(6);

                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.setScoreboard(board);

                    }
                }

                //
                if(plugin.isPhase(Phases.WAITING) && PlayersOnGame.getPlayers().size() == 1){
                    plugin.setPhase(Phases.STARTING);

                    //TIMER START & SET PLAYING PHASE
                    AutoStartTimer start =  new AutoStartTimer(plugin);
                    start.runTaskTimer(plugin, 0, 20);
                }

            }else if(plugin.isPhase(Phases.WAITING) && PlayersOnGame.getPlayers().size() >= 2){
                p.sendMessage(ChatColor.RED + "Connexion impossible, la partie est pleine !");
            }

            if(plugin.isPhase(Phases.ZERO)){
                p.sendMessage(ChatColor.RED + "Aucune partie n'est disponible, pour en créer une utilisez la commande /quakeslabcreate");
            }

            if(plugin.isPhase(Phases.STARTING)){
                if(!PlayersOnGame.getPlayers().contains(p)){
                    p.sendMessage(ChatColor.RED + "La partie est déjà en cours de lancement.");
                } else {
                    return true;
                }
            }

            if(plugin.isPhase(Phases.PLAYING)){
                if(!PlayersOnGame.getPlayers().contains(p)){
                    p.sendMessage(ChatColor.RED + "La partie est déjà en cours.");

                    TextComponent joinGameText = new TextComponent("[REJOINDRE]");
                    joinGameText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez-ici pour rejoindre la partie en tant que spectateur !").create()));
                    joinGameText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gamemode spectator"));
                    joinGameText.setColor(net.md_5.bungee.api.ChatColor.BOLD);
                    TextComponent joinGameAltText = new TextComponent("en tant que spectateur ! ");
                    p.spigot().sendMessage(joinGameText, joinGameAltText);
                }else{
                    p.sendMessage(ChatColor.RED + "Vous êtes déjà dans la partie.");
                }

            }

            if(plugin.isPhase(Phases.FINISH)){
                p.sendMessage(ChatColor.YELLOW + "La partie vient de se terminer, attendez la prochaine partie pour jouer ou créez en une avec /quakeslabcreate.");
            }

        }

        return true;
    }
}
