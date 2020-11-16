package com.jrooke.DeathSwap;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Swap {
	private Player player;
	private Player target;
	private Location targetLocation;
	private Collection<Chunk> targetChunks;
	private Plugin plugin;

	public Swap(Plugin plugin, Player player, Player target) {
		this.plugin = plugin;
		this.player = player;
		this.target = target;
	}

	public void preloadChunks() {
		targetChunks = getChunksAroundChunk(target.getLocation().getChunk());
		for (Chunk targetChunk : targetChunks) {
			targetChunk.load();
			targetChunk.setForceLoaded(true);
		}
	}

	public void prime() {
		targetLocation = target.getLocation();
	}

	public void activate() {
		player.teleport(targetLocation);
		Bukkit.broadcastMessage("Teleported " + ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + " to "
				+ ChatColor.YELLOW + target.getDisplayName() + "'s " + ChatColor.WHITE + "location!");
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	for(Chunk targetChunk : targetChunks) {            		
        			targetChunk.setForceLoaded(false);
            	}
            }
        }, 100L);
		
	}

	private Collection<Chunk> getChunksAroundChunk(Chunk chunk) {
		//int[] offset = { -1, 0, 1 };
		int[] offset = { 0 };

		World world = chunk.getWorld();
		int baseX = chunk.getX();
		int baseZ = chunk.getZ();

		Collection<Chunk> chunksAroundPlayer = new HashSet<>();
		for (int x : offset) {
			for (int z : offset) {
				Chunk newChunk = world.getChunkAt(baseX + x, baseZ + z);
				chunksAroundPlayer.add(newChunk);
			}
		}
		return chunksAroundPlayer;
	}
}
