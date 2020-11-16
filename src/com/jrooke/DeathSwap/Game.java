package com.jrooke.DeathSwap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.jrooke.Sound.Pitch;
import com.jrooke.Sound.SoundHelper;

public class Game extends BukkitRunnable implements Listener{
	private int tickCounter;
	private final int roundLength;
	private List<Player> players;
	private List<Swap> swaps;
	private Plugin plugin;
	
	public Game(Plugin plugin, int roundLength) {
		this.plugin = plugin;
		tickCounter = this.roundLength = roundLength;
		players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		swaps = new ArrayList<Swap>();
	}
	
	@EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		SoundHelper.playJingle(Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, players, SoundHelper.MEDIUM_DELAY, new float[]{Pitch.G3S,Pitch.G3,Pitch.F3S});
		Player player = event.getEntity();
		players.remove(player);
		Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + " is out!!!!");
		if(onePlayerRemains()) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + players.get(0).getDisplayName() + ChatColor.GREEN+" has won!!!");
			plugin.stop();
		}
    }

	@Override
	public void run() {	
		if (tickCounter == roundLength) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Starting Death Swap!");
			Collections.shuffle(players);
		}
		
		if (isMinuteMultiple()) {
			Bukkit.broadcastMessage(ChatColor.RED +pluralize(getMinutesRemaining(),"minute") + " remaining!");
			SoundHelper.playJingle(Sound.BLOCK_NOTE_BLOCK_CHIME, players,SoundHelper.SHORT_DELAY, new float[]{Pitch.G4S,Pitch.G4,Pitch.F4S});
		}
		
		if (isLast20Seconds()) {
			Bukkit.broadcastMessage(ChatColor.RED + pluralize(getSecondsRemaining(),"second") + " remaining!");
			players.stream().forEach((player)->SoundHelper.playJingleHeartbeat(player));
		}
		
		tickCounter--;
		
		if(tickCounter == 10) {
			for(int i = 0; i < players.size(); i++) {
				int targetPlayerIndex = i == players.size()-1 ? 0 : i+1;
				swaps.add(new Swap(plugin, players.get(i),players.get(targetPlayerIndex)));
			}
			swaps.stream().forEach((swap) -> {
				swap.preloadChunks();});
		}
		
		if (tickCounter <= 0) {
			
			for(Swap s : swaps) {
				s.prime();
			}
			for(Swap s : swaps) {
				s.activate();
			}
			swaps.clear();
			tickCounter = roundLength;
		}
	}
	
	private String pluralize(Integer amount, String word) {
		if(amount > 1) {
			word += "s";
		}
		return amount + " " + word;
	}

	private boolean onePlayerRemains() {
		return players.size() == 1;
	}

	private boolean isMinuteMultiple() {
		return getSecondsRemaining() % 60 == 0 && tickCounter % 20 == 0 && tickCounter > 0;
	}

	private boolean isLast20Seconds() {
		return getSecondsRemaining() <= 20 && tickCounter % 20 == 0 && tickCounter > 0;
	}

	private Integer getSecondsRemaining() {
		return tickCounter / 20;
	}
	
	private Integer getMinutesRemaining() {
		return Math.round(getSecondsRemaining() / 60);
	}

	public void skipRound() {
		tickCounter = 200;
	}

	public void setRoundTime() {
		// TODO Auto-generated method stub
		
	}
}
