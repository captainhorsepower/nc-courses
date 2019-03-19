package com.netcracker.courses.oop.music.digital.composition;

import com.netcracker.courses.oop.music.MusicGenre;

public class CompressedComposition extends AbstractDigitalComposition {

    public static final int    MAX_BITRATE     = 320;
    public static final int    MIN_BITRATE     = 128;
    public static final int    BITS_IN_BYTE    = 8;
    public static final int    BYTES_IN_MB     = 1024;

    private final int bitRate;

    public CompressedComposition(String compositionName, String artist,
                                 MusicGenre genre, int releaseYear,
                                 int duration, int bitRate, DigitalCompositionFormat format) {

        super(compositionName, artist, genre, releaseYear, duration, format);

        if (bitRate < MIN_BITRATE || bitRate > MAX_BITRATE) {
            throw new IllegalArgumentException(
                    bitRate
                    + " is invalid bit rate for compressed song("
                    + compositionName + " );"
            );
        }

        if(format.isLossless()) {
            throw new IllegalArgumentException(
                    "lossless format "
                    + format
                    + " is not applicable for compressed song("
                    + compositionName + " );"
            );
        }

        this.bitRate = bitRate;
    }

    @Override
    public double getSize() {
        return ((double) (getDurationSeconds() * (bitRate / BITS_IN_BYTE))) / BYTES_IN_MB;
    }
}
