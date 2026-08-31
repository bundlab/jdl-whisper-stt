package com.bundlab.util;

import ai.djl.modality.audio.Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;

public class AudioUtils {

    public static Audio loadAudio(Path path) throws IOException {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path.toFile())) {

            AudioFormat originalFormat = ais.getFormat();

            // Target format required by Whisper: 16kHz, mono, 16-bit signed, little-endian
            AudioFormat targetFormat = new AudioFormat(
                    16000.0f,   // sample rate
                    16,         // bits
                    1,          // mono
                    true,       // signed
                    false       // little endian
            );

            AudioInputStream converted = AudioSystem.getAudioInputStream(targetFormat, ais);

            byte[] rawBytes = converted.readAllBytes();
            int numSamples = rawBytes.length / 2;

            float[] samples = new float[numSamples];
            ByteBuffer buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.LITTLE_ENDIAN);

            for (int i = 0; i < numSamples; i++) {
                short pcmValue = buffer.getShort();
                samples[i] = pcmValue / 32768.0f;   // normalize to [-1.0, 1.0]
            }

            // Correct constructor for DJL 0.30.0 Audio class: Audio(float[] samples, float sampleRate)
            return new Audio(samples, 16000.0f);
        }
    }
}