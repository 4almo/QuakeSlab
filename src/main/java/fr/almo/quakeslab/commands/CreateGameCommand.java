package fr.almo.quakeslab.commands;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!QuakeSlab.getPlugin().isPhase(Phases.ZERO)){
                p.sendMessage("Une partie est déjà en cours.");
            } else {

                if(args.length == 0){

                    p.sendMessage(ChatColor.YELLOW + "Votre partie vient d'être crée.");
                    QuakeSlab.getPlugin().setPhase(Phases.WAITING);

                    for (Player players : Bukkit.getOnlinePlayers()){

                        TextComponent joinGameText = new TextComponent("[REJOINDRE]");
                        joinGameText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez-ici pour rejoindre la partie !").create()));
                        joinGameText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/quakeslabjoin"));
                        joinGameText.setColor(net.md_5.bungee.api.ChatColor.BOLD);
                        TextComponent joinGameAltText = new TextComponent("Une partie de QuakeSlab vient d'être crée ! ");
                        players.spigot().sendMessage(joinGameAltText, joinGameText);

                    }

                } else {
                    p.sendMessage(ChatColor.RED + "Commande inconnue, essayez /quakeslabcreate pour créer une partie de QuakeSlab.");
                }


            }


        }

        return false;
    }
}
