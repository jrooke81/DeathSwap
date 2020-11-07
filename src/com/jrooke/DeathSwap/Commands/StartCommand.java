package com.jrooke.DeathSwap.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.jrooke.DeathSwap.Plugin;
import com.jrooke.DeathSwap.PluginConstants;

public class StartCommand implements CommandExecutor {
	
	private Plugin plugin;
	
	public StartCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (Bukkit.getOnlinePlayers().size() < 2) {
			Bukkit.broadcastMessage(PluginConstants.InsufficientPlayers);
		}
		else {
			return plugin.start();
		}
		return false;
	}


}
