package com.netcracker.courses.oop.music;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * base class for all music compositions of any type
 * contains only some general type independent information about composition
 */
public abstract class AbstractMusicComposition {

    /* all fields are private due to encapsulation reasons
     * as they are not intended to be directly accessed
     * from any other classes nor subclasses
     */

    /* this field must be static, but you extension says
     * that I break naming convention if they are static.
     * (as I can guess)
     * */
//    private final String DEFAULT_LYRICS = "lyrics were removed due to copyright claim.";

    private final String        name;
    private final String        artist;
    private final int           releaseYear;
    private final MusicGenre    genre;

    /* currently unused but might be used for future features (like play, pause...) */
//    private final String        lyrics;


    public AbstractMusicComposition(String compositionName, String artist,
                                    MusicGenre genre, int releaseYear) {

        if (releaseYear < 0 || releaseYear > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("invalid release year for "
                    + "composition " + compositionName);
        }

        this.name = compositionName;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }


    public String getName() {
        return name;
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

}
