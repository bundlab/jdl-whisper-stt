package com.bundlab.service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AudioRecorder {

    private static final int SAMPLE_RATE = 16000;
    private static final int SAMPLE_SIZE = 16;
    private static final int CHANNELS = 1;

    public Path record(int seconds) throws Exception {
        Path outputDir = Paths.get("audio");
        Files.createDirectories(outputDir);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path outputFile = outputDir.resolve("recording_" + timestamp + ".wav");

        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Microphone not supported");
        }

        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        Thread recordingThread = new Thread(() -> {
            try (AudioInputStream ais = new AudioInputStream(line)) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, outputFile.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        recordingThread.start();

        System.out.println("Recording... (Press Enter to stop early or wait " + seconds + "s)");
        Thread.sleep(seconds * 1000L);

        line.stop();
        line.close();

        recordingThread.join(500);

        System.out.println("✅ Recording saved: " + outputFile);
        return outputFile;
    }
}