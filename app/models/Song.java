package models;

import javax.persistence.*;
import models.Artist;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @SequenceGenerator(name="song_id_seq",
            sequenceName="song_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="song_id_seq")
    @Column(name = "id", updatable=false)
    public int id;

    @Column(name="title")
    public String title;

    @Column(name="uri")
    public String uri;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="artist_id")
    private Artist artist;

    public void setArtist(Artist artist) {
        this.artist = artist;
        if (!artist.getSongs().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            artist.getSongs().add(this);
        }
    }

    public Artist getArtist() {
        return artist;
    }
}
