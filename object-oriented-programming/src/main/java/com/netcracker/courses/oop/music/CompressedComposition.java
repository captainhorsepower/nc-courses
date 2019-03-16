package com.netcracker.courses.oop.music;

public class CompressedComposition extends AbstractDigitalComposition {

    /* this fields should be static, but extension */
    public final int    MAX_BITRATE     = 320;
    public final int    MIN_BITRATE     = 128;
    private final int   BITS_IN_BYTE    = 8;
    private final int   BYTES_IN_MB     = 1024;


    private final int bitRate;

    public CompressedComposition(String compositionName, String author, int releaseYear,
                                 int duration, int bitRate, DigitalCompositionFormat format) {

        super(compositionName, author, releaseYear, duration, format);

        if (bitRate < 128 || bitRate > 320) {
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

    public CompressedComposition(String compositionName, String author, int releaseYear,
                                 int duration, int bitRate) {

        this(compositionName, author, releaseYear,
                duration, bitRate, DigitalCompositionFormat.MP3);
    }

    @Override
    public double getSize() {
        return ((double) (getDurationSeconds() * (bitRate / BITS_IN_BYTE))) / BYTES_IN_MB;
    }
}
