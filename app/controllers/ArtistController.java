package controllers;

import models.Artist;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import javax.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.create_artist;
import views.html.view_artist;
import javax.persistence.*;
import play.db.jpa.JPAApi;

public class ArtistController extends Controller {


    @Inject
    FormFactory formFactory;

    @Inject
    JPAApi jpaApi;

    public Result create() {
        final Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        return ok(create_artist.render(form));
    }

    @Transactional
    public Result store() {
        EntityManager em = jpaApi.em();
        Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        Artist nArtist = form.get();

        Integer id = em.createNamedQuery("Artist.maxId",Integer.class)
                .getSingleResult();
        if(id==null) {
            id=0;
        }
        nArtist.setId(++id);

        //TODO: catch the correct exception (check stackoverflow)
        try{
            em.persist(nArtist);
        }catch(Exception e){
            form.reject("username","user already exist");
            return badRequest(create_artist.render(form));
        }

        return redirect(controllers.routes.ArtistController.show(nArtist.getId()));
    }

    @Transactional(readOnly=true)
    public Result show(int artist_id) {
        Artist artist = JPA.em().find(Artist.class,artist_id);
        if(artist!=null){
            final Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
            return ok(view_artist.render(artist));
        }else{
            return ok("artist does not exist");
        }
    }

    public Result edit(int artist){
        return ok("Estoy editando una cuenta");
    }

    public Result update(int artist){
        return ok("estoy actualizando una cuenta");
    }

    @Transactional
    public Result delete(int artist_id){
        Artist artist = JPA.em().find(Artist.class, artist_id);
        if(artist!=null){
            JPA.em().remove(artist);
            return redirect("Artist deleted succesfully");
        }else{
            return ok("artist does not exist");
        }
    }

}
