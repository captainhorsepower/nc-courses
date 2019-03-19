package com.netcracker.courses.oop.music.digital;

import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;

import java.util.*;

public class MusicCD {

    public static final int SORT_BY_NAME = 10;
    public static final int SORT_BY_DURATION = 20;
    public static final int SORT_BY_SIZE = 30;
    public static final int SORT_BY_ARTIST = 40;
    public static final int SORT_BY_GENRE = 50;

    private final String diskName;
    private final int totalFreeSpaceMB;
    private double freeSpaceMB;
    private int durationSeconds;

    /* use ArrayList for faster sorts */
    private ArrayList<AbstractDigitalComposition> songs;

    /**
     * creates new CD for music of given size
     * @param totalFreeSpaceMB CD init free space
     * @param diskName CD name (id)
     */
    public MusicCD(int totalFreeSpaceMB, String diskName) {
        this.totalFreeSpaceMB = totalFreeSpaceMB;
        this.diskName = diskName;

        freeSpaceMB = totalFreeSpaceMB;
        durationSeconds = 0;

        songs = new ArrayList<>();
    }

    /**
     * attempt to write given composition to CD
     * @param composition given composition
     * @return false, if there is not enough space for composition. Otherwise true
     */
    public boolean addComposition(AbstractDigitalComposition composition) {

        if (composition.getSize() > freeSpaceMB) return false;

        songs.add(composition);
        freeSpaceMB -= composition.getSize();
        durationSeconds += composition.getDurationSeconds();

        return true;
    }

    /**
     * attempt to write given songs to CD
     * @param compilation given songs
     * @return List of compositions, that did not fit on CD. null if songs fits
     */
    public List<AbstractDigitalComposition> addAllCompositions(Collection<AbstractDigitalComposition> compilation) {
        List<AbstractDigitalComposition> skippedCompositions = null;

        for (AbstractDigitalComposition c : compilation) {
            if (!addComposition(c)) {
                if (skippedCompositions == null) {

                    /* use LinkedList as it's free to expand */
                    skippedCompositions = new LinkedList<>();
                }
                skippedCompositions.add(c);
            }
        }

        return skippedCompositions;
    }

    public double getFreeSpaceMB() {
        return freeSpaceMB;
    }

    public int getTotalFreeSpaceMB() {
        return totalFreeSpaceMB;
    }

    public String getCDName() {
        return diskName;
    }

    public List<AbstractDigitalComposition> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public int getDurationSeconds(){
        return durationSeconds;
    }

    /**
     * sorts collection according to identifier;
     * @param sortBy identifier
     */
    public void sort(final int sortBy) {
        Comparator<AbstractDigitalComposition> comparator;

        switch (sortBy) {
            case SORT_BY_ARTIST:
                comparator = Comparator.comparing(AbstractDigitalComposition::getArtist);
                break;
            case SORT_BY_DURATION:
                comparator = Comparator.comparing(AbstractDigitalComposition::getDurationSeconds);
                break;
            case SORT_BY_GENRE:
                comparator = Comparator.comparing(AbstractDigitalComposition::getGenre);
                break;
            case SORT_BY_NAME:
                comparator = Comparator.comparing(AbstractDigitalComposition::getCompositionName);
                break;
            case SORT_BY_SIZE:
                comparator = Comparator.comparing(AbstractDigitalComposition::getSize);
                break;
            default:
                comparator = Comparator.comparing(AbstractDigitalComposition::getArtist)
                        .thenComparing(AbstractDigitalComposition::getReleaseYear);
                break;
        }

        songs.sort(comparator);
    }

    /**
     * finds composition that fits in given range of params in this CD
     * @param minSize <=
     * @param maxSize >=
     * @param minReleaseYear <=
     * @param maxReleaseYear >=
     * @return composition, or null in nothing found
     */
    public AbstractDigitalComposition findSong(double minSize, double maxSize,
                                               int minReleaseYear, int maxReleaseYear) {

        if ((minSize < 0. || minReleaseYear < 0)
                || (maxSize < minSize || maxReleaseYear < minReleaseYear)) {
            throw new IllegalArgumentException("invalid boundaries");
        }

        AbstractDigitalComposition result = null;

        for (AbstractDigitalComposition c : songs) {
            int year = c.getReleaseYear();
            double size = c.getSize();

            if ((year <= maxReleaseYear && year >= minReleaseYear)
                    && (size <= maxSize && size >= minSize)) {
                result = c;
                break;
            }
        }
        return result;
    }
}