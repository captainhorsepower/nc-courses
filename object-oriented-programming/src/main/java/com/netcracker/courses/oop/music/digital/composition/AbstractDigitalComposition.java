package com.netcracker.courses.oop.music.digital.composition;

import com.netcracker.courses.oop.music.AbstractMusicComposition;
import com.netcracker.courses.oop.music.MusicGenre;

public abstract class AbstractDigitalComposition extends AbstractMusicComposition {
    private final int duration;
    private final DigitalCompositionFormat format;

    public AbstractDigitalComposition(String compositionName, String artist,
                                      MusicGenre genre, int releaseYear,
                                      int duration, DigitalCompositionFormat format) {
        super(compositionName, artist, genre, releaseYear);

        if (duration <= 0) {
            throw new IllegalArgumentException("illegal duration ("
                    + duration + ") for composition "
                    + compositionName);
        }

        this.duration = duration;
        this.format = format;
    }


    /**
     * This value is composition-format dependent
     * @return size of composition in MB
     */
    abstract public double getSize();

    /**
     * @return composition duration as String "min:sec"
     */
    public  String getDurationStirng() {
        return String.format("%2d:%02d", (duration / 60), (duration % 60));
    }

    /**
     * @return composition duration in seconds
     */
    public int getDurationSeconds() {
        return duration;
    }

    public DigitalCompositionFormat getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return String.format(
                "%-70s %-7s %5s %7.1f MB",

                super.toString(),
                ("." + format),
                getDurationStirng(),
                getSize());
    }
}