package com.netcracker.courses.oop.music.digital;

/**
 * General interface for stuff that can be played
 * e.g. song, album...
 * I will implement this on songs and CDs later today, if I have time util deadline
 */
public interface Playable {
    void play();
    void pause();
    void stop();
}
