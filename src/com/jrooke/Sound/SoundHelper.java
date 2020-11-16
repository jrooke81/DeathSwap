package com.jrooke.Sound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.jrooke.DeathSwap.Plugin;

public class SoundHelper {

	public static Plugin plugin;
	public static int TINY_DELAY = 3;
	public static int SHORT_DELAY = 5;
	public static int MEDIUM_DELAY = 10;
	public static int LARGE_DELAY = 20;

	public static void playJingle(Sound soundType, Player[] players, int delay, float p1, float p2, float p3) {
		for (Player p : players) {
			playJingle(soundType, p, delay, new float[] { p1, p2, p3 });
		}
	}

	public static void playJingle(Sound soundType, Player player, int delay, float p1, float p2, float p3) {
		playJingle(soundType, player, delay, new float[] { p1, p2, p3 });
	}

	public static void playJingle(Sound soundType, List<Player> players, int delay, float[] notes) {
		for (Player p : players) {
			playJingle(soundType, p, delay, notes);
		}
	}

	public static void playJingle(Sound soundType, Player player, int delay, float[] notes) {
		playJingle(soundType, player, delay, notes, 1);
	}

	public static void playJingle(Sound soundType, Player player, int delay, float[] notes, float volume) {
		List<SoundParam> soundsToPlay = new ArrayList<SoundParam>();
		for (float p : notes) {
			soundsToPlay.add(new SoundParam(p, soundType, volume));
		}
		PlaySoundTask soundTask = new PlaySoundTask(soundsToPlay, delay, player);
		soundTask.runTaskTimer(plugin, 0L, 1L);
	}

	public static void playJingleCEG(Sound soundType, Player player) {
		playJingle(soundType, player, MEDIUM_DELAY, Pitch.C4, Pitch.E4, Pitch.G4);
	}

	public static void playJingleGEC(Sound soundType, Player player) {
		playJingle(soundType, player, LARGE_DELAY, Pitch.C4, Pitch.E4, Pitch.G4);
	}

	public static void playJingleEGC(Sound soundType, List<Player> players) {
		for (Player p : players) {
			playJingle(soundType, p, TINY_DELAY, Pitch.C4, Pitch.E4, Pitch.G4);
		}
	}

	public static void playJingleSuccess(Sound soundType, Collection<Player> players) {
		for (Player p : players) {
			playJingleSuccess(soundType, p);
		}
	}

	public static void playJingleHeartbeat(Player player) {
		playJingle(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, player, SoundHelper.TINY_DELAY,
				new float[] { Pitch.F3S, Pitch.F3S });
	}

	public static void playJingleSuccess(Sound soundType, Player player) {
		playJingle(soundType, player, SHORT_DELAY, new float[] { Pitch.G3, Pitch.A3, Pitch.B3, Pitch.D4, Pitch.G4 });

	}

	public static void playJingleFailure(Sound soundType, Player player) {
		playJingle(soundType, player, SHORT_DELAY, new float[] { Pitch.A3S, Pitch.A3, Pitch.G3S, Pitch.G3, Pitch.F3S });
	}
}
