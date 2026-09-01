package com.bundlab.service;

import ai.djl.training.util.ProgressBar;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.audio.Audio;
import ai.djl.audio.translator.WhisperTranslatorFactory;   // ← correct package
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.bundlab.util.AudioUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.UnsupportedAudioFileException;

public class WhisperService implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(WhisperService.class);

    private final ZooModel<Audio, String> model;
    private final Predictor<Audio, String> predictor;

    public WhisperService() throws ModelException, IOException {
        logger.info("Loading Whisper tiny.en model via DJL...");

        Criteria<Audio, String> criteria = Criteria.builder()
            .setTypes(Audio.class, String.class)
            .optModelUrls("djl://ai.djl.huggingface.pytorch/openai/whisper-tiny.en")
            .optEngine("PyTorch")
            .optTranslatorFactory(new WhisperTranslatorFactory())
            .optProgress(new ai.djl.training.util.ProgressBar())
            .build();

        this.model = criteria.loadModel();
        this.predictor = model.newPredictor();

        logger.info("✅ Whisper model loaded successfully!");
    }

    public String transcribe(Path audioPath) throws TranslateException, IOException, UnsupportedAudioFileException {
        Audio audio = AudioUtils.loadAudio(audioPath);
        return predictor.predict(audio);
    }

    @Override
    public void close() {
        if (predictor != null) {
            predictor.close();
        }
        if (model != null) {
            model.close();
        }
        logger.info("WhisperService closed.");
    }
}