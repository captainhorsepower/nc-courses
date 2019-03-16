package com.netcracker.courses.oop.music;

public abstract class AbstractDigitalComposition extends AbstractMusicComposition {
    private final int duration;
    private final DigitalCompositionFormat format;

    public AbstractDigitalComposition(String compositionName, String author, int releaseYear, int duration, DigitalCompositionFormat format) {
        super(compositionName, author, releaseYear);
        this.duration = duration;
        this.format = format;
    }


    /**
     * This value is composition-format dependent
     * @return size of composition in MB
     */
    abstract public int getSize();

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