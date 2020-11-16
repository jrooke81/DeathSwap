package com.jrooke.Sound;

import org.bukkit.Sound;

public class SoundParam {
	private Sound soundType;
	private float pitch;
	private float volume;

	public SoundParam(float pitch, Sound soundType) {
		this(pitch, soundType, 1);
	}

	public SoundParam(float pitch, Sound soundType, float volume) {
		this.pitch = pitch;
		this.soundType = soundType;
		this.volume = volume;
	}

	public Sound getSoundType() {
		return soundType;
	}

	public float getPitch() {
		return pitch;
	}

	public float getVolume() {
		return volume;
	}
}
