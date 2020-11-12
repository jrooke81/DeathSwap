package com.jrooke.DeathSwap.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jrooke.DeathSwap.Plugin;

public class SetRoundTimeCommand implements CommandExecutor {

	private Plugin plugin;

	public SetRoundTimeCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		try {
			if (args.length == 1) {
				plugin.setRoundTime(Integer.parseInt(args[0]));
				return true;
			}
		}
		catch(NumberFormatException e) {
			sender.sendMessage("Time argument must be a whole number, in seconds.");
		}
		return false;
	}

}
