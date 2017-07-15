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
import javax.persistence.*;
import play.db.jpa.JPAApi;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class SongController extends Controller {

    @Inject
    FormFactory formFactory;

    @Inject
    JPAApi jpaApi;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("mostrando index de canciones");
    }
    public Result show(int id){
        return ok("crmostrando una cancion");
    }
    @Transactional
    public Result destroy(int id){
        EntityManager em = jpaApi.em();
        Song song = em.find(Song.class, id);
        if(song!=null){
            Integer artist_id = song.getArtist().getId();
            em.remove(song);
            return redirect(controllers.routes.ArtistController.show(id));
        }else{
            return ok("Song does not exist");
        }
    }

    public Result create(Integer artist_id){
        return ok(upload_song.render(artist_id,""));
    }

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
                    File songDir = new File("./songs");
                    if (!songDir.exists()) {
                        songDir.mkdir();
                    }
                    File artistDir = new File(songDir.getPath()+"/"+artist_id);
                    if (!artistDir.exists()) {
                        artistDir.mkdir();
                    }
                    java.nio.file.Path source = java.nio.file.Paths.get(songFile.getPath());
                    java.nio.file.Path dest = java.nio.file.Paths.get(artistDir.getPath()+"/"+fileName);
                    java.nio.file.CopyOption[] options = new java.nio.file.CopyOption[]{
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                            java.nio.file.StandardCopyOption.COPY_ATTRIBUTES
                    };
                    java.nio.file.Files.copy(source, dest, options);
                    EntityManager em = jpaApi.em();
                    Integer id = em.createNamedQuery("Song.maxId",Integer.class)
                            .getSingleResult();
                    if(id==null) {
                        id=0;
                    }
                    Artist artist = em.find(Artist.class, artist_id);
                    Song song = new Song(++id, fileName,artist );
                    em.persist(song);
                    return redirect(controllers.routes.ArtistController.show(artist_id));
                }
                catch (Exception e){
                    e.printStackTrace();
                    return badRequest(upload_song.render(artist_id,"Error uploading file: "+e.getMessage()));
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
