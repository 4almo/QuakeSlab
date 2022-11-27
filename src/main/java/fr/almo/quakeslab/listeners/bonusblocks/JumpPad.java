package fr.almo.quakeslab.listeners.bonusblocks;

import fr.almo.quakeslab.QuakeSlab;
import fr.almo.quakeslab.manager.Phases;
import fr.almo.quakeslab.manager.PlayersOnGame;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPad implements Listener {

    private final QuakeSlab plugin;

    public JumpPad(QuakeSlab plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJumpPadBlock(PlayerMoveEvent e) {
        final Player p = e.getPlayer();

        if(plugin.isPhase(Phases.PLAYING)){

            if(PlayersOnGame.getPlayers().contains(p)){

                if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.PISTON_BASE) {

                    final Vector vec = p.getVelocity();
                    vec.setY(5.0);
                    p.setVelocity(vec);
                    p.getWorld().playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 0.5F);
                    p.sendMessage("ok" + vec);

                }

            }
        }
    }
}