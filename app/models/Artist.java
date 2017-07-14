package models;

import javax.persistence.*;
import models.Song;
import java.util.List;
import play.data.validation.Constraints;

/**
 * This class maps the table 'artist' from the database using JPA
 * @query Artist.maxId select the max id in the table
 */
@Entity
@Table(name = "artist",
        uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@NamedQuery(name="Artist.maxId", query="select max(a.id) from Artist a")
public class Artist {
    @Id
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "username",unique=true)
    @Constraints.Required
    private String username;

    @Column(name = "password")
    @Constraints.Required
    private String password;

    @Column(name = "forename")
    @Constraints.Required
    private String forename;

    @Column(name = "surname")
    @Constraints.Required
    private String surname;

    @OneToMany(mappedBy="artist")
    private List<Song> songs;

    public Artist(){

    }
    public Artist(Integer id, String username, String password, String forename, String surname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    public void addSong(Song song) {
        this.songs.add(song);
        if (song.getArtist() != this) {
            song.setArtist(this);
        }
    }

    public List<Song> getSongs() {
        return songs;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Artist{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", songs=" + songs +
                '}';
    }
}
