package fr.almo.quakeslab.listeners.bonusobjects;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import fr.almo.quakeslab.manager.PlayersOnGame;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Shield implements Listener {

    private final QuakeSlab plugin;

    public Shield(QuakeSlab plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShieldHold(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        Action a = e.getAction();

        if(!plugin.isPhase(Phases.PLAYING)){

            if(!PlayersOnGame.getPlayers().contains(p)){

                if(p.getInventory().getItemInHand().getType() == Material.BEDROCK){

                    if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){

                        p.sendMessage("nop");
                        //donne des infos

                    } else if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){

                        new BukkitRunnable(){

                            final double radius = 0.5;
                            double t = 0;
                            double yPlayerFeet = p.getEyeLocation().getY() - 1.8;
                            double yMaxLimit = p.getEyeLocation().getY() + 0.2;

                            float r = (float) 122/255;
                            float g = (float) 122/255;
                            float b = (float) 255/255;

                            @Override
                            public void run() {
                                Location playerLoc = p.getLocation();

                                t+= 0.5;
                                yPlayerFeet+= 0.05;

                                    double x = radius * Math.cos(t);
                                    double y = yPlayerFeet;
                                    double z = radius * Math.sin(t);

                                    playerLoc.add(x, y - playerLoc.getY(), z);

                                p.getWorld().spigot().playEffect(playerLoc, Effect.COLOURED_DUST, 0, 1, r, g, b, 1, 0, 30);

                                    playerLoc.subtract(x, y, z);

                                    if(yPlayerFeet > yMaxLimit){
                                        yPlayerFeet -= 2;
                                    }

                                    if(t > 300){
                                        this.cancel();
                                    }

                            }
                        }.runTaskTimer(QuakeSlab.getPlugin(), 0, 1);
                    p.sendMessage(ChatColor.WHITE + "Vous avez activé un " + ChatColor.BLUE + "" + ChatColor.BOLD + "BOUCLIER" + ChatColor.RESET + "" + ChatColor.WHITE + ".");

                    }

                }

            }

        }

    }

}
