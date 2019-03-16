package com.netcracker.courses.oop.music;

public enum DigitalCompositionFormat {
    MP3(false),
    FLACK(true),
    WAV(true);

    private boolean isLossless;

    DigitalCompositionFormat(boolean isLossless) {
        this.isLossless = isLossless;
    }

    public boolean isLossless() {
        return isLossless;
    }
}
