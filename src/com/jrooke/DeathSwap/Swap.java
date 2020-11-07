package com.jrooke.DeathSwap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Swap {
	private Player player;
	private Player target;
	private Location targetLocation;
	
	public Swap(Player player, Player target) {
		this.player = player;
		this.target = target;
	}
	
	public void prime() {
		targetLocation = target.getLocation();
		//Bukkit.broadcastMessage(target.getDisplayName()+"'s location is: "+targetLocation.toString() +". "+player.getDisplayName()+" will be teleported there.");
	}
	
	public void activate() {
		player.teleport(targetLocation);
		Bukkit.broadcastMessage("Teleported "+ChatColor.RED+player.getDisplayName()+ChatColor.WHITE+" to "+ChatColor.YELLOW+target.getDisplayName()+"'s "+ChatColor.WHITE+"location!");
	}
}
