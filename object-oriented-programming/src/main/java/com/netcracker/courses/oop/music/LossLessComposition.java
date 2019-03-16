package com.netcracker.courses.oop.music;

public class LossLessComposition extends AbstractDigitalComposition {

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
        // TODO: 3/16/2019 implement properly 
        return 0;
    }

}
