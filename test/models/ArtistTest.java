package controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import models.*;
public class ArtistTest {

    @Test
    public void testArtistGettersSetters() {
        String username="username",
                pass = "pass",
                forename="forename",
                surname="surname";
        Integer id = 4;

        Artist artist = new Artist();
        artist.setId(id);
        artist.setUsername(username);
        artist.setPassword(pass);
        artist.setForename(forename);
        artist.setSurname(surname);
        assertEquals("Problem with id",id, artist.getId());
        assertEquals("Problem with username",username, artist.getUsername());
        assertEquals("Problem with password",pass, artist.getPassword());
        assertEquals("Problem with forename",forename, artist.getForename());
        assertEquals("Problem with surname",surname, artist.getSurname());
    }
    @Test
    public void testArtistConstructor() {
        String username="username",
                pass = "pass",
                forename="forename",
                surname="surname";
        Integer id = 4;

        Artist artist = new Artist(id,username,pass,forename,surname);

        assertEquals("Problem with id",id, artist.getId());
        assertEquals("Problem with username",username, artist.getUsername());
        assertEquals("Problem with password",pass, artist.getPassword());
        assertEquals("Problem with forename",forename, artist.getForename());
        assertEquals("Problem with surname",surname, artist.getSurname());
    }

}
