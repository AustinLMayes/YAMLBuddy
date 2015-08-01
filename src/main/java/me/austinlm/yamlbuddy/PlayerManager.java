package me.austinlm.yamlbuddy;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;

public class PlayerManager implements Listener {
    private static HashMap<Player, BuddyPlayer> players = Maps.newHashMap();

    public static BuddyPlayer getPlayer(Player p) {
        if (players.containsKey(p))
            return players.get(p);
        BuddyPlayer pl = new BuddyPlayer(p);
        players.put(p, pl);
        return pl;
    }

    @EventHandler
    public void onPlayerLogoff(PlayerQuitEvent e) {
        players.remove(e.getPlayer());
    }

    public static Collection<BuddyPlayer> getPlayers() {
        return players.values();
    }
}
