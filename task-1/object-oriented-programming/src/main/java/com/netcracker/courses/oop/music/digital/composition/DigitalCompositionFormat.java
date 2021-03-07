package com.netcracker.courses.oop.music.digital.composition;

public enum DigitalCompositionFormat {
    MP3(false),
    FLAC(true),
    WAV(true);

    private boolean isLossless;

    DigitalCompositionFormat(boolean isLossless) {
        this.isLossless = isLossless;
    }

    public boolean isLossless() {
        return isLossless;
    }
}
