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

    /* this field must be static, but you extension says
     * that I break naming convention if they are static.
     * (as I can guess)
     * */
//    private final String DEFAULT_LYRICS = "lyrics were removed due to copyright claim.";

    private final String        name;
    private final String        author;
    private final int           releaseYear;
    private final MusicGenre    genre;

    /* currently unused but might be used for future features (like play, pause...) */
//    private final String        lyrics;


    public AbstractMusicComposition(String compositionName, String author, MusicGenre genre, int releaseYear) {
        this.name = compositionName;
        this.author = author;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }


    public String getName() {
        return name;
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
