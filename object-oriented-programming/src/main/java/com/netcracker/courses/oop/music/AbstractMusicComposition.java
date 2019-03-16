package com.netcracker.courses.oop.music;

/**
 * base class for all music compositions of any type
 * contains only some general type independent information about composition
 */
public abstract class AbstractMusicComposition {

    /* all fields are private due to encapsulation reasons
     * as they are not intended to be directly accessed
     * from any other classes nor subclasses
     */
    private static final String DEFAULT_LYRICS = "lyrics were removed due to copyright claim.";
    private static final MusicGenre DEFAULT_GENRE = MusicGenre.UNKNOWN_GENRE;

    private final String        author;
    private final int           releaseYear;
    private final String        lyrics;
    private final MusicGenre    genre;

    public AbstractMusicComposition(String author, int releaseYear) {
        this.author = author;
        this.releaseYear = releaseYear;
        this.genre = AbstractMusicComposition.DEFAULT_GENRE;
        this.lyrics = AbstractMusicComposition.DEFAULT_LYRICS;
    }

    // TODO: 3/16/2019 add constructor with lyrics and genre


    public String getLyrics() {
        return lyrics;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}
