package com.jrooke.DeathSwap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Game extends BukkitRunnable implements Listener{
	private int tickCounter;
	private final int roundLength;
	private List<Player> players;
	private List<Swap> swaps;
	
	public Game(Plugin plugin) {
		tickCounter = roundLength = Integer.valueOf(plugin.getConfig().getString("roundLength"))*20;
		players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		swaps = new ArrayList<Swap>();
	}
	
	@EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		Player player = event.getEntity();
		players.remove(player);
		Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + " is out!!!!");
		for(Player p : players) {
			Bukkit.broadcastMessage(p.getDisplayName() + " is still in the game.");
		}
    }

	@Override
	public void run() {	
		if (tickCounter == roundLength) {
			Collections.shuffle(players);
		}
		
		tickCounter--;
		
		if (tickCounter <= 0) {
			for(int i = 0; i < players.size(); i++) {
				int targetPlayerIndex = i == players.size()-1 ? 0 : i+1;
				swaps.add(new Swap(players.get(i),players.get(targetPlayerIndex)));
			}
			for(Swap s : swaps) {
				s.prime();
			}
			for(Swap s : swaps) {
				s.activate();
			}
			swaps.clear();
			tickCounter = roundLength;
		}
		
		if (isMinuteMultiple()) {
			Bukkit.broadcastMessage(getSecondsRemaining()/60+" minute(s) remaining!");
		}
		
		if (isLast20Seconds()) {
			Bukkit.broadcastMessage(getSecondsRemaining()+" seconds remaining!");
		}
	}
	
	private boolean isMinuteMultiple() {
		return getSecondsRemaining() % 60 == 0 && tickCounter % 20 == 0 && tickCounter < 0;
	}

	private boolean isLast20Seconds() {
		return getSecondsRemaining() <= 20 && tickCounter % 20 == 0;
	}

	private Integer getSecondsRemaining() {
		return tickCounter / 20;
	}
	
}
