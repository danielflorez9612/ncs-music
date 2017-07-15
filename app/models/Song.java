package models;

import javax.persistence.*;
import models.Artist;

@Entity
@Table(name = "song")
@NamedQuery(name="Song.maxId", query="select max(s.id) from Song s")
public class Song {
    @Id
    @Column(name = "id", updatable=false)
    public Integer id;

    @Column(name="title")
    public String title;

    @Column(name="uri")
    public String uri;



    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="artist_id")
    private Artist artist;

    public Song(){

    }
    public Song(Integer id, String filename,Artist artist){
        this.id = id;
        this.title = filename.substring(0,filename.indexOf(".mp3"));
        setArtist(artist);
        this.uri = "./songs/"+artist.getId()+"/"+filename;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        if (!artist.getSongs().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            artist.getSongs().add(this);
        }
    }

    public Artist getArtist() {
        return artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public String toString(){
        return this.uri;
    }
}
