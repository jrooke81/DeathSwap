package com.jrooke.DeathSwap;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.jrooke.DeathSwap.Commands.SkipCommand;
import com.jrooke.DeathSwap.Commands.StartCommand;
import com.jrooke.DeathSwap.Commands.StopCommand;

public class Plugin extends JavaPlugin{
	private Game pluginTask;
	private boolean isRunning;

	public Plugin() {
		isRunning = false;
		saveDefaultConfig();
	}

	public boolean start() {
		if (!isRunning) {
			pluginTask = new Game(this);
			getServer().getPluginManager().registerEvents(pluginTask, this);
			pluginTask.runTaskTimer(this, 0L, 1L);
			return isRunning = true;
		}
		return false;
	}
	
	public void onEnable() {
		this.getCommand("startds").setExecutor(new StartCommand(this));
		this.getCommand("stopds").setExecutor(new StopCommand(this));
		this.getCommand("skipds").setExecutor(new SkipCommand(this));
	}

	public boolean stop() {
		if (isRunning && pluginTask != null) {
			pluginTask.cancel();
			HandlerList.unregisterAll(pluginTask);
			isRunning = false;
			return true;
		}
		return false;
	}

	public boolean skip() {
		if(pluginTask != null) {
			pluginTask.skipRound();
			return true;
		}
		return false;
	}
}
