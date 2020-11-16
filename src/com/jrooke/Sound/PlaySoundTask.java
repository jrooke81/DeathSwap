package com.jrooke.Sound;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaySoundTask extends BukkitRunnable {

	private int counter;
	private int soundIndex;
	private List<SoundParam> soundsToPlay;
	private int delay;
	private Player player;

	public PlaySoundTask(List<SoundParam> soundsToPlay, int delay, Player player) {
		this.soundsToPlay = soundsToPlay;
		this.player = player;
		soundIndex = 0;
		if (delay <= 0) {
			throw new IllegalArgumentException("delay must be greater than 0");
		} else {
			this.counter = this.delay = delay;
		}
	}

	@Override
	public void run() {
		if (soundIndex >= soundsToPlay.size()) {
			this.cancel();
			return;
		}
		if (counter == delay) {
			SoundParam currentSound = soundsToPlay.get(soundIndex);
			player.playSound(player.getLocation(), currentSound.getSoundType(), currentSound.getVolume(),
					currentSound.getPitch());
		}
		if (counter > 0) {
			counter--;
		} else {
			counter = delay;
			soundIndex++;
		}

	}

}
