package com.jailbreackers.soulhunter

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

// @ Author Qaisar Mukhtar
class SoundPlayer(context: Context) {
    private var audioAttributes: AudioAttributes? = null
    internal val SOUND_POOL_MAX = 2

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            soundPool = SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build()
        } else {
            soundPool = SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0)
        }
        hitSound = soundPool.load(context, R.raw.coin, 1)

    }

    fun playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    companion object {
        private lateinit var soundPool: SoundPool
        private var hitSound: Int = 0

    }
}