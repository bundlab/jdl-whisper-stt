# JDL Whisper STT (AI Meeting Assistant)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/)
[![DJL](https://img.shields.io/badge/DJL-0.30.0-blue.svg)](https://djl.ai/)
[![PyTorch](https://img.shields.io/badge/Engine-PyTorch-red.svg)](https://pytorch.org/)
[![Whisper](https://img.shields.io/badge/Model-Whisper-green.svg)](https://github.com/openai/whisper)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Maven](https://img.shields.io/badge/Build-Maven-brightgreen.svg)](https://maven.apache.org/)

**Offline Speech-to-Text in pure Java using Deep Java Library (DJL) and OpenAI Whisper**

A high-performance speech recognition application built entirely in Java. It uses Deep Java Library (DJL) with the PyTorch engine to run OpenAI's Whisper model — **no Python runtime required**.

## Why PyTorch?

Whisper was originally trained using PyTorch (Python).  
Deep Java Library (DJL) allows us to load and run the same PyTorch model **directly from Java** by using the PyTorch native engine under the hood.

- You write and run pure Java code
- DJL automatically downloads and manages the PyTorch native libraries
- No need to install Python or call any Python scripts

## Features

- Offline speech-to-text using Whisper (`tiny.en` by default)
- Real-time microphone recording
- Transcribe existing WAV files
- Fully written in Java
- Automatic model + native library download on first run
- Maven project with fat JAR packaging

## Tech Stack

- **Java 17**
- **Deep Java Library (DJL)** 0.30.0
- **PyTorch Engine** (via DJL)
- **OpenAI Whisper** (`openai/whisper-tiny.en`)
- **Maven**

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Working microphone (for recording)

## Project Structure

```
jdl-whisper-stt/
├── pom.xml
├── README.md
├── LICENSE
├── .gitignore
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
└── audio/                  # Generated at runtime
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

> **First run note**: DJL will automatically download the Whisper model and PyTorch native libraries. This can take several minutes depending on your internet speed.

## Usage

After launching the app, you will see a simple menu:

1. Record and transcribe (5 seconds)
2. Record for custom duration
3. Transcribe an existing WAV file
4. Exit

## Changing the Whisper Model

By default, the project uses `whisper-tiny.en` for speed.  
You can change it to a more accurate model in `WhisperService.java`:

- `openai/whisper-base.en`
- `openai/whisper-small.en`
- `openai/whisper-medium.en`

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

**Abdullahi Bundi**  
Maiduguri / Borno, Nigeria

