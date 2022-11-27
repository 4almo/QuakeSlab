package fr.almo.quakeslab.utilitys;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoStartTimer extends BukkitRunnable {

//
private final QuakeSlab plugin;

    public AutoStartTimer (QuakeSlab plugin) {
        this.plugin = plugin;
    }
//
    private int timer = 10;

    @Override
    public void run() {

            if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
                for(Player players : Bukkit.getOnlinePlayers()){

                    players.sendMessage(ChatColor.GRAY + "Lancement du jeu dans " + ChatColor.YELLOW + timer);
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 0);

            }

        }

        if(timer == 0){

            for(Player players : Bukkit.getOnlinePlayers()){
                players.sendMessage(ChatColor.YELLOW + "C'est parti !");
                players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 0);
                players.setHealth(20);
                players.setFoodLevel(20);
                players.setGameMode(GameMode.ADVENTURE);
                players.setOp(true);
            }

            plugin.setPhase(Phases.PLAYING);
            cancel();

        }

        timer --;

    }


}
