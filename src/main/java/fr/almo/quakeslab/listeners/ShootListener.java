package fr.almo.quakeslab.listeners;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import fr.almo.quakeslab.manager.PlayersOnGame;
import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;


public class ShootListener implements Listener {

    //
    private final QuakeSlab plugin;

    public ShootListener (QuakeSlab plugin){
        this.plugin = plugin;
    }
    //

    @EventHandler
    public void onShoot(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        Action a = e.getAction();

        if(QuakeSlab.getPlugin().isPhase(Phases.PLAYING)){

            if(p.getInventory().getItemInHand().getType() == Material.STICK){

                if(a == Action.LEFT_CLICK_BLOCK){
                    p.sendMessage("lol");
                    p.getWorld().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE, 5);

                }else if(a == Action.RIGHT_CLICK_AIR ){
                    p.getWorld().playSound(p.getLocation(), Sound.WITHER_HURT, 1, 0);
                    new BukkitRunnable(){
                        double t = 0;
                        Location loc = p.getEyeLocation().subtract(0, -0.5, 0);
                        Vector direction = loc.getDirection().normalize();

                        float r = (float) 122/255;
                        float g = (float) 255/255;
                        float b = (float) 0/255;

                        @Override
                        public void run(){
                            t += 0.1    ;
                            double x = direction.getX() * t;
                            double y = direction.getY() * t;
                            double z = direction.getZ() * t;

                            loc.add(x, y, z);
                            p.getWorld().playEffect(loc, Effect.WITCH_MAGIC, 1);
                            p.getWorld().playEffect(loc, Effect.PORTAL, 1);
                            p.getWorld().spigot().playEffect(loc, Effect.COLOURED_DUST, 0, 1, r, g, b, 1, 0, 30);

                            List<Player> playersOnGame = PlayersOnGame.getPlayers();
                            for (Entity target : p.getWorld().getEntities()){

                                //player hauteur head 0,43
                                //player hauteur body 1.37 moitié = 0,685
                                //player largeur body = 0,625
                                //player largeur head = 0,437
                                //1 pixel = 0,0625

                                if (loc.distanceSquared(target.getLocation().add(0,1.4,0)) <= 0.3f)
                                    p.sendMessage("entité touchée" + target.getName());
                                    if (target.equals(playersOnGame) && !target.equals(p)){
                                        Damageable targetdamage = (Damageable) target;
                                        targetdamage.damage(20);
                                        p.sendMessage("Tu as tué" + " " + target.getName());
                                        Bukkit.broadcastMessage(p.getName() + " " + "à tué" + " " + target.getName());

                                    }

                            }

                            loc.subtract(x, y, z);

                            Location point = loc.add(direction);

                            Vector hitblockface = point.getDirection();
                            if(point.getBlock().getType().isSolid()){
                                p.getWorld().playEffect(point, Effect.HEART, 100);
                                p.sendMessage("ça a hit un bloc");
                                this.cancel();

                                if(p.isSneaking()){

                                    new BukkitRunnable(){
                                        double t = 0;

                                        @Override
                                        public void run(){
                                            t += 0.5;
                                            double x = hitblockface.getX() * t;
                                            double y = hitblockface.getY() * t;
                                            double z = hitblockface.getZ() * t;
                                            loc.add(-x, -y, -z);
                                            p.getWorld().playEffect(loc, Effect.HEART, 1);

                                        }

                                    }.runTaskTimer(QuakeSlab.getPlugin(), 0, 1);

                                }

                            }

                            if (t > 50){
                                p.sendMessage("le temps a stop");
                                this.cancel();

                            }

                        }
                    }.runTaskTimer(QuakeSlab.getPlugin(), 0, 1);

                }
            }

        }




    }

}
