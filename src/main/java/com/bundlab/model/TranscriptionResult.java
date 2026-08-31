package com.bundlab.model;

public record TranscriptionResult(
        String text,
        double confidence,
        long processingTimeMs
) {
}