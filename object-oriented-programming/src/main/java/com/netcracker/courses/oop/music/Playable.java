package com.netcracker.courses.oop.music;

/**
 * General interface for stuff that can be played
 * e.g. song, album...
 */
public interface Playable {
    void play();
    void pause();
    void stop();
}
