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
        return (duration/60) + ":" + (duration%60);
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
}