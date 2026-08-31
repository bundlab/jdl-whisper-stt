package com.bundlab;

import com.bundlab.service.AudioRecorder;
import com.bundlab.service.WhisperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Scanner;

public class WhisperSTTApp {

    private static final Logger logger = LoggerFactory.getLogger(WhisperSTTApp.class);

    public static void main(String[] args) {
        logger.info("🚀 Starting JDl Whisper Speech-to-Text Application");

        WhisperService whisperService;
        try {
            whisperService = new WhisperService();
        } catch (Exception e) {
            logger.error("Failed to initialize WhisperService", e);
            System.out.println("❌ Error: Could not load Whisper model: " + e.getMessage());
            return;
        }

        AudioRecorder recorder = new AudioRecorder();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   Deep Java Library - Whisper Speech to Text");
            System.out.println("=".repeat(60));

            while (true) {
                System.out.println("\nOptions:");
                System.out.println("1. Record and transcribe (5 seconds)");
                System.out.println("2. Record for custom duration");
                System.out.println("3. Transcribe existing WAV file");
                System.out.println("4. Exit");

                System.out.print("\nChoose option: ");
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> recordAndTranscribe(recorder, whisperService, 5);
                        case "2" -> {
                            System.out.print("Enter duration in seconds: ");
                            int seconds = Integer.parseInt(scanner.nextLine().trim());
                            recordAndTranscribe(recorder, whisperService, seconds);
                        }
                        case "3" -> {
                            System.out.print("Enter path to WAV file: ");
                            String filePath = scanner.nextLine().trim();
                            transcribeFile(whisperService, filePath);
                        }
                        case "4" -> {
                            System.out.println("👋 Goodbye!");
                            whisperService.close();
                            return;
                        }
                        default -> System.out.println("❌ Invalid option");
                    }
                } catch (Exception e) {
                    logger.error("Error during operation", e);
                    System.out.println("❌ Error: " + e.getMessage());
                }
            }
        } finally {
            if (whisperService != null) {
                whisperService.close();
            }
        }
    }

    private static void recordAndTranscribe(AudioRecorder recorder, WhisperService whisper, int seconds)
            throws Exception {
        System.out.println("🎙️ Recording for " + seconds + " seconds... Speak now!");
        Path audioFile = recorder.record(seconds);

        System.out.println("⏳ Transcribing with Whisper...");
        long start = System.currentTimeMillis();

        String transcription = whisper.transcribe(audioFile);

        long duration = System.currentTimeMillis() - start;

        System.out.println("\n✅ Transcription completed in " + duration + "ms");
        System.out.println("📝 Result: " + transcription);
    }

    private static void transcribeFile(WhisperService whisper, String filePath) throws Exception {
        Path path = Path.of(filePath);
        if (!path.toFile().exists()) {
            System.out.println("❌ File not found: " + filePath);
            return;
        }

        System.out.println("⏳ Transcribing " + path.getFileName() + "...");
        String result = whisper.transcribe(path);
        System.out.println("\n📝 Transcription: " + result);
    }
}