package com.netcracker.courses.oop.music.digital.composition;

import com.netcracker.courses.oop.music.MusicGenre;

public class LossLessComposition extends AbstractDigitalComposition {

    /* should be static */
    public final double MB_PER_SEC = 0.17;

    public LossLessComposition(String compositionName, String author,
                               MusicGenre genre, int releaseYear,
                               int duration, DigitalCompositionFormat format) {
        super(compositionName, author, genre, releaseYear, duration, format);

        if (!format.isLossless()) {
            throw new IllegalArgumentException(
                    "compressing format "
                    + format
                    + " is invalid for lossless song("
                    + compositionName + " );"
            );
        }
    }

    @Override
    public double getSize() {
        double size = MB_PER_SEC * getDurationSeconds();
        return (getFormat() != DigitalCompositionFormat.WAV) ? size : size * 2;
    }

}
