package fr.almo.quakeslab.listeners.bonusobjects;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import fr.almo.quakeslab.manager.PlayersOnGame;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Smoke implements Listener {
//
    private final QuakeSlab plugin;

    public Smoke(QuakeSlab plugin) {
        this.plugin = plugin;
    }
//
    @EventHandler
    public void onSmokeLaunch(ProjectileLaunchEvent e){
        final Player p = (Player) e.getEntity().getShooter();
        Entity entity = e.getEntity();


        if(plugin.isPhase(Phases.PLAYING)){

            if(PlayersOnGame.getPlayers().contains(p)){

                if(p.getInventory().getItemInHand().getType() == Material.SNOW_BALL){

                    p.sendMessage(ChatColor.GRAY + "Vous avez lancé un fumigène !");
                    p.getWorld().playSound(p.getLocation(), Sound.FUSE, 1, 0);

                    new BukkitRunnable(){
                        double t = 0;

                        @Override
                        public void run(){
                            t +=0.5;

                            Location entityLocation = entity.getLocation();

                            //trail effect
                            p.getWorld().playEffect(entityLocation, Effect.SMOKE, 3);
                            p.getWorld().playEffect(entityLocation, Effect.SMALL_SMOKE, 1);

                            if(entityLocation.getBlock().getType().isSolid()) {
                                this.cancel();
                                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BOOOM");
                                //hit effect
                                p.getWorld().playEffect(entityLocation, Effect.HEART, 5);

                            } else if (t > 10){
                                this.cancel();
                                p.sendMessage("impact non touché");
                            }

                        }
                    }.runTaskTimer(QuakeSlab.getPlugin(), 0, 1);

            }

        }


        }

    }


    @EventHandler
    public void onBonusObject(ProjectileHitEvent e){
        final Player p = (Player) e.getEntity().getShooter();
        Location impactlocation = e.getEntity().getLocation();

        if(plugin.isPhase(Phases.PLAYING)){

            if(PlayersOnGame.getPlayers().contains(p)){

                if(e.getEntity().getType() == EntityType.SNOWBALL){

                    p.getWorld().playEffect(impactlocation, Effect.SMOKE, 30, 10);
                    p.getWorld().playSound(impactlocation, Sound.FUSE, 1, 0);

                    new BukkitRunnable(){

                        double phi = 0;

                        @Override
                        public void run() {


                            phi+= Math.PI / 30;

                            for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI/30){

                                double r = 3;
                                double x = r*cos(theta)*sin(phi);
                                double y = r*cos(phi);
                                double z = r*sin(theta)*sin(phi);

                                impactlocation.add(x,y,z);
                                p.getWorld().playEffect(impactlocation, Effect.SMOKE, 10);

                                impactlocation.subtract(x,y,z);

                            }

                            if(phi > 10 * Math.PI){
                                this.cancel();
                            }

                        }


                    }.runTaskTimer(QuakeSlab.getPlugin(), 0, 1);

                }
            }
        }
    }

}

