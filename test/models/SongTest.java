package controllers;

import static org.junit.Assert.*;
import org.junit.Test;
import models.*;

/**
 * Class that tests the song entity
 */
public class SongTest {

    /**
     * Tests the getters and setters for the song entity
     */
    @Test
    public void testSongGettersSetters() {
        Integer id = 4;
        String title="title",uri = "uri";

        Song song = new Song();
        song.setId(id);
        song.setTitle(title);
        song.setUri(uri);

        assertEquals("Problem with id",id, song.getId());
        assertEquals("Problem with title",title, song.getTitle());
        assertEquals("Problem with uri",uri, song.getUri());
    }

}
