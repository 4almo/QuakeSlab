package fr.almo.quakeslab;

import fr.almo.quakeslab.commands.CreateGameCommand;
import fr.almo.quakeslab.commands.JoinGameCommand;
import fr.almo.quakeslab.listeners.bonusblocks.JumpPad;
import fr.almo.quakeslab.listeners.bonusobjects.Shield;
import fr.almo.quakeslab.listeners.bonusobjects.Smoke;
import fr.almo.quakeslab.listeners.ShootListener;
import fr.almo.quakeslab.manager.Phases;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuakeSlab extends JavaPlugin {

    private static QuakeSlab plugin;

    private Phases currentPhase;

    //Plugin start
    @Override
    public void onEnable() {

        plugin = this;

        setPhase(Phases.ZERO);

        //Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ShootListener(plugin), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JumpPad(plugin), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Smoke(plugin), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Shield(plugin), this);

        //Commands
        getCommand("quakeslabcreate").setExecutor(new CreateGameCommand());
        getCommand("quakeslabjoin").setExecutor(new JoinGameCommand(plugin));

    }

    public static QuakeSlab getPlugin() {
        return plugin;
    }

    public void setPhase(Phases currentPhase){
        this.currentPhase = currentPhase;

    }

    public boolean isPhase(Phases currentPhase){
        return this.currentPhase == currentPhase;

    }

}
