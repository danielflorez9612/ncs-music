package controllers;

import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import models.*;
import play.db.jpa.Transactional;
import play.data.FormFactory;
import javax.inject.Inject;
import views.html.upload_song;
import java.io.File;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.JPAApi;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
/**
 * This controller contains an action to handle HTTP requests
 * and execute querys into the song table.
 */
public class SongController extends Controller {
    /**
     * Injects the formFactory needed to create forms
     */
    @Inject
    FormFactory formFactory;

    /**
     * Injects the JPAApi needed to obtain the EntityManager
     */
    @Inject
    JPAApi jpaApi;

    /**
     * Deletes the song with the id given
     * @param id the id of the song to delete
     * @return Response that shows the artist that had the song or a bad request
     */
    @Transactional
    public Result destroy(int id){
        EntityManager em = jpaApi.em();
        Song song = em.find(Song.class, id);
        if(song!=null){
            Integer artist_id = song.getArtist().getId();
            em.remove(song);
            return redirect(controllers.routes.ArtistController.show(artist_id));
        }else{
            return badRequest("Song does not exist");
        }
    }

    /**
     * Shows the form that uploads a form for an artist
     * @param artist_id the id of the artist that uploads the song
     * @return Response that shows the form to upload a song
     */
    public Result create(Integer artist_id){
        return ok(upload_song.render(artist_id,""));
    }

    /**
     * Uploads a song for an artist to cloudinary
     * @param artist_id the id of the artist that uploads the song
     * @return Response that goes to the artist that uploaded the song or a the form to upload it with errors
     */
    @Transactional
    public Result store(Integer artist_id){
        MultipartFormData<File> body = request().body().asMultipartFormData();
        FilePart<File> songFP = body.getFile("song");
        if (songFP != null) {
            String fileName = songFP.getFilename();

            if(fileName.endsWith(".mp3")|| fileName.endsWith(".MP3")){
                String contentType = songFP.getContentType();
                File songFile = songFP.getFile();
                songFile.setReadable(true, false);
                songFile.setExecutable(true, false);
                songFile.setWritable(true, false);
                try
                {
                    Map config = new HashMap();
                    config.put("cloud_name", "fearlessmonkeycloud");
                    config.put("api_key", "346117855196925");
                    config.put("api_secret", "66jReAGXErVJ7-ScK510PVxzkeU");
                    Cloudinary cloudinary = new Cloudinary(config);
                    Map uploadResult = cloudinary.uploader().upload(songFile, ObjectUtils.asMap(
                            "resource_type", "auto"
                    ));
                    EntityManager em = jpaApi.em();
                    Integer id = em.createNamedQuery("Song.maxId",Integer.class)
                            .getSingleResult();
                    if(id==null) {
                        id=0;
                    }
                    Artist artist = em.find(Artist.class, artist_id);
                    Song song = new Song(++id, fileName,artist );
                    song.setUri(uploadResult.get("url").toString());
                    em.persist(song);
                    return redirect(controllers.routes.ArtistController.show(artist_id));
                }
                catch (Exception e){
                    e.printStackTrace();
                    return badRequest(upload_song.render(artist_id,"Error uploading file: "+e.getMessage()+" -- "+e.getCause()));
                }
            }else{
                return badRequest(upload_song.render(artist_id,"song must be a mp3 file"));
            }

        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }
}
