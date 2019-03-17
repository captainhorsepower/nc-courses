package com.netcracker.courses.oop.music.digital.composition;

public class LossLessComposition extends AbstractDigitalComposition {

    /* should be static */
    public final double MB_PER_SEC = 0.17;

    public LossLessComposition(String compositionName, String author, int releaseYear,
                               int duration, DigitalCompositionFormat format) {
        super(compositionName, author, releaseYear, duration, format);

        if (!format.isLossless()) {
            throw new IllegalArgumentException(
                    "compressing format "
                    + format
                    + " is invalid for lossless song("
                    + compositionName + " );"
            );
        }
    }

    public LossLessComposition(String compositionName, String author, int releaseYear, int duration) {
        this(compositionName, author, releaseYear, duration, DigitalCompositionFormat.FLAC);
    }

    @Override
    public double getSize() {
        double size = MB_PER_SEC * getDurationSeconds();
        return (getFormat() == DigitalCompositionFormat.WAV) ? size : size * 2;
    }

}
