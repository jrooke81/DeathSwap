package com.jrooke.DeathSwap.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jrooke.DeathSwap.Plugin;

public class StopCommand implements CommandExecutor {

	private Plugin plugin;

	public StopCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return plugin.stop();
	}

}
