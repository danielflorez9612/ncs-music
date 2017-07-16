package controllers;

import static org.junit.Assert.*;
import org.junit.Test;
import models.*;

public class SongTest {

    @Test
    public void testSong() {
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
