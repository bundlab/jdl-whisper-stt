# JDL Whisper STT

**Speech-to-Text application built with Deep Java Library (DJL) and OpenAI Whisper**

A pure Java implementation of offline speech recognition using the Whisper model via Deep Java Library. No Python required.

## Features

- Offline speech-to-text using Whisper (tiny.en model by default)
- Microphone recording support
- Transcribe existing WAV files
- Built entirely in Java with Deep Java Library (DJL)
- Automatic model and native library download on first run
- Clean Maven project structure

## Tech Stack

- **Java 17**
- **Deep Java Library (DJL)** 0.30.0
- **PyTorch Engine** (via DJL)
- **OpenAI Whisper** (`whisper-tiny.en`)
- **Maven**

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Microphone (for recording)

## Project Structure

```
jdl-whisper-stt/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/bundlab/
│       │       ├── WhisperSTTApp.java
│       │       ├── service/
│       │       │   ├── WhisperService.java
│       │       │   └── AudioRecorder.java
│       │       └── util/
│       │           └── AudioUtils.java
│       └── resources/
└── audio/                  # Recorded files (generated at runtime)
```

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/jdl-whisper-stt.git
cd jdl-whisper-stt
```

### 2. Build the project

```bash
mvn clean package
```

### 3. Run the application

```bash
java -jar target/jdl-whisper-stt-1.0.0-jar-with-dependencies.jar
```

> **Note**: On the first run, DJL will automatically download the Whisper model and PyTorch native libraries. This may take several minutes depending on your internet connection.

## Usage

When the application starts, you will see a simple menu:

1. Record and transcribe (5 seconds)
2. Record for custom duration
3. Transcribe an existing WAV file
4. Exit

## Important Notes

- Audio is recorded and processed at **16 kHz mono** (Whisper requirement).
- The default model is `openai/whisper-tiny.en` for speed. You can change it to `whisper-base.en` or `whisper-small.en` in `WhisperService.java` for better accuracy.
- Make sure your system has a working microphone.

## License

This project is open source and available under the MIT License.

## Author

**Abdullahi Bundi**  
Maiduguri / Borno, Nigeria


---



