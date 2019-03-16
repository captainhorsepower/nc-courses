package com.netcracker.courses.oop.music;

public abstract class AbstractDigitalComposition extends AbstractMusicComposition {
    private final int duration;

    public AbstractDigitalComposition(String compositionName, String author, int releaseYear, int duration) {
        super(compositionName, author, releaseYear);
        this.duration = duration;
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
}