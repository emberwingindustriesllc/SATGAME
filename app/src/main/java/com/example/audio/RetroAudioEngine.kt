package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin
import kotlin.random.Random

enum class BgmTrack {
    MAP_EXPRESS,
    BATTLE
}

object RetroAudioEngine {
    private val scope = CoroutineScope(Dispatchers.Default)
    private const val SAMPLE_RATE = 22050

    var soundEnabled: Boolean = true
    var musicEnabled: Boolean = true
        set(value) {
            field = value
            if (!value) {
                stopBgm()
            } else if (currentTrack != null) {
                startBgm(currentTrack!!)
            }
        }

    private var bgmJob: Job? = null
    var currentTrack: BgmTrack? = null
        private set

    // === UI & Interaction SFX ===
    fun playSelect() {
        if (!soundEnabled) return
        scope.launch {
            playTone(frequency = 880.0, durationMs = 35, waveType = WaveType.SQUARE)
        }
    }

    fun playConfirm() {
        if (!soundEnabled) return
        scope.launch {
            playTone(frequency = 659.25, durationMs = 40, waveType = WaveType.SQUARE)
            playTone(frequency = 1046.5, durationMs = 60, waveType = WaveType.SQUARE)
        }
    }

    // === Mega Man Inspired Combat SFX ===
    fun playMegaBuster() {
        if (!soundEnabled) return
        scope.launch {
            playSweep(startFreq = 980.0, endFreq = 260.0, durationMs = 60)
        }
    }

    fun playMegaChargeBlast() {
        if (!soundEnabled) return
        scope.launch {
            playTone(frequency = 523.25, durationMs = 30, waveType = WaveType.SQUARE)
            playTone(frequency = 1046.50, durationMs = 40, waveType = WaveType.SQUARE)
            playSweep(startFreq = 1400.0, endFreq = 300.0, durationMs = 120)
            playNoise(durationMs = 90, decay = true)
        }
    }

    fun playBossExplosion() {
        if (!soundEnabled) return
        scope.launch {
            playNoise(durationMs = 160, decay = true)
            playSweep(startFreq = 280.0, endFreq = 80.0, durationMs = 180)
            playNoise(durationMs = 200, decay = true)
        }
    }

    fun playTeleportWarp() {
        if (!soundEnabled) return
        scope.launch {
            val arpeggio = listOf(261.63, 329.63, 392.00, 523.25, 659.25, 783.99, 1046.50)
            for (freq in arpeggio) {
                playTone(frequency = freq, durationMs = 25, waveType = WaveType.SQUARE)
            }
        }
    }

    fun playEnergyRecover() {
        if (!soundEnabled) return
        scope.launch {
            repeat(4) {
                playTone(frequency = 880.0, durationMs = 25, waveType = WaveType.SQUARE)
                delay(35)
                playTone(frequency = 1174.66, durationMs = 25, waveType = WaveType.SQUARE)
                delay(35)
            }
        }
    }

    // === Train Whistle SFX for Realm Express ===
    fun playTrainWhistle() {
        if (!soundEnabled) return
        scope.launch {
            // Dual chord whistle (F#5 and A#5 chord)
            playDualChord(freq1 = 739.99, freq2 = 932.33, durationMs = 250)
            delay(50)
            playDualChord(freq1 = 739.99, freq2 = 932.33, durationMs = 400)
        }
    }

    // === Pokémon Battle SFX ===
    fun playTackle() {
        if (!soundEnabled) return
        scope.launch {
            playNoise(durationMs = 90, decay = true)
        }
    }

    fun playCriticalHit() {
        if (!soundEnabled) return
        scope.launch {
            playTone(frequency = 440.0, durationMs = 40, waveType = WaveType.SQUARE)
            playTone(frequency = 880.0, durationMs = 50, waveType = WaveType.SQUARE)
            playTone(frequency = 1320.0, durationMs = 90, waveType = WaveType.SQUARE)
            playNoise(durationMs = 100, decay = true)
        }
    }

    fun playMiss() {
        if (!soundEnabled) return
        scope.launch {
            playSweep(startFreq = 400.0, endFreq = 180.0, durationMs = 120)
        }
    }

    fun playBallThrow() {
        if (!soundEnabled) return
        scope.launch {
            playSweep(startFreq = 300.0, endFreq = 950.0, durationMs = 110)
        }
    }

    fun playBallWobble() {
        if (!soundEnabled) return
        scope.launch {
            playTone(frequency = 523.25, durationMs = 60, waveType = WaveType.SQUARE)
        }
    }

    fun playCaptureSuccess() {
        if (!soundEnabled) return
        scope.launch {
            val notes = listOf(523.25, 659.25, 783.99, 1046.50, 1318.51)
            for (freq in notes) {
                playTone(frequency = freq, durationMs = 80, waveType = WaveType.SQUARE)
            }
        }
    }

    fun playLevelUp() {
        if (!soundEnabled) return
        scope.launch {
            val arpeggio = listOf(440.0, 554.37, 659.25, 880.0, 1108.73, 1318.5)
            for (freq in arpeggio) {
                playTone(frequency = freq, durationMs = 65, waveType = WaveType.SQUARE)
            }
        }
    }

    fun playFaint() {
        if (!soundEnabled) return
        scope.launch {
            playSweep(startFreq = 600.0, endFreq = 120.0, durationMs = 250)
        }
    }

    fun playEncounterStart() {
        if (!soundEnabled) return
        scope.launch {
            playSweep(startFreq = 200.0, endFreq = 600.0, durationMs = 150)
        }
    }

    // === Background 8-Bit Chiptune Music Synthesizer ===
    fun startBgm(track: BgmTrack) {
        currentTrack = track
        if (!musicEnabled) return
        bgmJob?.cancel()
        bgmJob = scope.launch {
            try {
                while (isActive && musicEnabled) {
                    when (track) {
                        BgmTrack.MAP_EXPRESS -> playMapExpressTheme()
                        BgmTrack.BATTLE -> playBattleTheme()
                    }
                }
            } catch (_: CancellationException) {}
        }
    }

    fun stopBgm() {
        bgmJob?.cancel()
        bgmJob = null
    }

    private suspend fun playMapExpressTheme() {
        // Cheerful, upbeat 8-bar melodic loop (Route 1 / Realm Express)
        // Key of C major / G major
        val melody = listOf(
            Pair(523.25, 200), // C5
            Pair(659.25, 200), // E5
            Pair(783.99, 200), // G5
            Pair(659.25, 200), // E5
            Pair(880.00, 250), // A5
            Pair(783.99, 250), // G5
            Pair(587.33, 300), // D5
            Pair(523.25, 200), // C5
            Pair(493.88, 200), // B4
            Pair(523.25, 350)  // C5
        )

        for ((freq, dur) in melody) {
            if (!musicEnabled || bgmJob?.isActive != true) break
            playTone(frequency = freq, durationMs = dur - 30, waveType = WaveType.SQUARE)
            delay(dur.toLong())
        }
        delay(300)
    }

    private suspend fun playBattleTheme() {
        // Fast, energetic battle rhythm (Pokémon / Mega Man style)
        val battleNotes = listOf(
            Pair(440.0, 120),  // A4
            Pair(440.0, 120),
            Pair(523.25, 120), // C5
            Pair(587.33, 140), // D5
            Pair(659.25, 140), // E5
            Pair(587.33, 120),
            Pair(523.25, 120),
            Pair(440.0, 200),
            Pair(392.0, 120),  // G4
            Pair(440.0, 240)
        )

        for ((freq, dur) in battleNotes) {
            if (!musicEnabled || bgmJob?.isActive != true) break
            playTone(frequency = freq, durationMs = dur - 25, waveType = WaveType.SQUARE)
            delay(dur.toLong())
        }
        delay(200)
    }

    private enum class WaveType {
        SQUARE, SINE
    }

    private fun playTone(frequency: Double, durationMs: Int, waveType: WaveType) {
        val numSamples = (SAMPLE_RATE * durationMs / 1000.0).toInt().coerceAtLeast(1)
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val angle = 2.0 * Math.PI * frequency * t
            val sampleVal = when (waveType) {
                WaveType.SQUARE -> if (sin(angle) >= 0) 0.45 else -0.45
                WaveType.SINE -> sin(angle) * 0.5
            }
            val envelope = 1.0 - (i.toDouble() / numSamples)
            buffer[i] = (sampleVal * envelope * Short.MAX_VALUE).toInt().toShort()
        }
        writeAndPlay(buffer)
    }

    private fun playDualChord(freq1: Double, freq2: Double, durationMs: Int) {
        val numSamples = (SAMPLE_RATE * durationMs / 1000.0).toInt().coerceAtLeast(1)
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val sample1 = if (sin(2.0 * Math.PI * freq1 * t) >= 0) 0.3 else -0.3
            val sample2 = if (sin(2.0 * Math.PI * freq2 * t) >= 0) 0.3 else -0.3
            val envelope = 1.0 - (i.toDouble() / numSamples)
            buffer[i] = ((sample1 + sample2) * envelope * Short.MAX_VALUE).toInt().toShort()
        }
        writeAndPlay(buffer)
    }

    private fun playSweep(startFreq: Double, endFreq: Double, durationMs: Int) {
        val numSamples = (SAMPLE_RATE * durationMs / 1000.0).toInt().coerceAtLeast(1)
        val buffer = ShortArray(numSamples)
        var phase = 0.0
        for (i in 0 until numSamples) {
            val progress = i.toDouble() / numSamples
            val currentFreq = startFreq + (endFreq - startFreq) * progress
            phase += 2.0 * Math.PI * currentFreq / SAMPLE_RATE
            val sampleVal = if (sin(phase) >= 0) 0.4 else -0.4
            val envelope = 1.0 - progress
            buffer[i] = (sampleVal * envelope * Short.MAX_VALUE).toInt().toShort()
        }
        writeAndPlay(buffer)
    }

    private fun playNoise(durationMs: Int, decay: Boolean) {
        val numSamples = (SAMPLE_RATE * durationMs / 1000.0).toInt().coerceAtLeast(1)
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val raw = (Random.nextDouble() * 2.0 - 1.0) * 0.55
            val envelope = if (decay) 1.0 - (i.toDouble() / numSamples) else 1.0
            buffer[i] = (raw * envelope * Short.MAX_VALUE).toInt().toShort()
        }
        writeAndPlay(buffer)
    }

    private fun writeAndPlay(buffer: ShortArray) {
        try {
            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            scope.launch {
                delay((buffer.size * 1000L / SAMPLE_RATE) + 60)
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }
}
