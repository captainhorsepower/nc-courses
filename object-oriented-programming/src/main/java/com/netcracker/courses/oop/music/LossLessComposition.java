package com.netcracker.courses.oop.music;

public class LossLessComposition extends AbstractDigitalComposition {
    private final DigitalCompositionFormat format;

    public LossLessComposition(String compositionName, String author, int releaseYear,
                               int duration, DigitalCompositionFormat format) {
        super(compositionName, author, releaseYear, duration);

        if (!format.isLossless()) {
            throw new IllegalArgumentException(
                    "compressing format "
                    + format
                    + " is invalid for lossless song("
                    + compositionName + " );"
            );
        }

        this.format = format;
    }

    public LossLessComposition(String compositionName, String author, int releaseYear, int duration) {
        this(compositionName, author, releaseYear, duration, DigitalCompositionFormat.FLACK);
    }

    @Override
    public double getSize() {
        // TODO: 3/16/2019 implement properly 
        return 0;
    }

    @Override
    public DigitalCompositionFormat getFormat() {
        // TODO: 3/16/2019 remove from here along with rafactoring
        return null;
    }
}
