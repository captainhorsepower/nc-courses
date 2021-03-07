package com.netcracker.courses.oop.music;

import java.time.LocalDateTime;

/**
 * base class for all music compositions of any type
 * contains only some general type independent information about composition
 */
public abstract class AbstractMusicComposition {

    /* all fields are private due to encapsulation reasons
     * as they are not intended to be directly accessed
     * from any other classes nor subclasses
     */

//    private final String DEFAULT_LYRICS = "lyrics were removed due to copyright claim.";

    private final String        compositionName;
    private final String        artist;
    private final int           releaseYear;
    private final MusicGenre    genre;

    /* currently unused but might be used for future features (like play, pause...) */
//    private final String        lyrics;


    public AbstractMusicComposition(String compositionName, String artist,
                                    MusicGenre genre, int releaseYear) {

        if (releaseYear <= 0 || releaseYear > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("invalid release year for "
                    + "composition " + compositionName);
        }

        this.compositionName = compositionName;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }


    public String getCompositionName() {
        return compositionName;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return String.format(
                "%-20s %-25s %-7s %-10s"
                , getCompositionName()
                , getArtist()
                , releaseYear
                , getGenre()
        );
    }
}
