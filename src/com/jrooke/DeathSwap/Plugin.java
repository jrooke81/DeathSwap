package com.jrooke.DeathSwap;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.jrooke.DeathSwap.Commands.SetRoundTimeCommand;
import com.jrooke.DeathSwap.Commands.SkipCommand;
import com.jrooke.DeathSwap.Commands.StartCommand;
import com.jrooke.DeathSwap.Commands.StopCommand;

public class Plugin extends JavaPlugin{
	private Game pluginTask;
	private boolean isRunning;
	private int roundLength;

	public Plugin() {
		isRunning = false;
		roundLength = Integer.valueOf(getConfig().getString("roundLength")) * 20;
		saveDefaultConfig();
	}

	public boolean start() {
		if (!isRunning) {
			pluginTask = new Game(this, roundLength);
			getServer().getPluginManager().registerEvents(pluginTask, this);
			pluginTask.runTaskTimer(this, 0L, 1L);
			return isRunning = true;
		}
		return false;
	}
	
	public void onEnable() {
		this.getCommand("startDs").setExecutor(new StartCommand(this));
		this.getCommand("stopDs").setExecutor(new StopCommand(this));
		this.getCommand("skipDs").setExecutor(new SkipCommand(this));
		this.getCommand("setDsRoundTime").setExecutor(new SetRoundTimeCommand(this));
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

	public void setRoundTime(int roundLength) {
		this.roundLength = roundLength;
		Bukkit.broadcastMessage("Round length has been set to "+roundLength+" seconds.");
	}
}
