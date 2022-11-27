package fr.almo.quakeslab.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersOnGame {

        private static List<Player> listplayers = new ArrayList<>();

        public static List<Player> getPlayers(){
            return listplayers;
        }

}
