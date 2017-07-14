package controllers;

import models.Artist;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import javax.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.artist;
import javax.persistence.*;


public class ArtistController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result create() {
        final Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        return ok(artist.render(form));
    }

    @Transactional
    public Result store() {
        Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        Artist nArtist = form.get();

        Integer id = JPA.em().createNamedQuery("Artist.maxId",Integer.class)
                .getSingleResult();
        if(id==null) {
            id=0;
        }
        nArtist.setId(++id);
        try{
            JPA.em().persist(nArtist);
            return ok(nArtist.toString());
        }catch(RollbackException e){
            form.reject("username","user already exist");
            return badRequest(artist.render(form));
        }
    }

    public Result show(int artist) {
        return ok("estoy mostrando una cuenta");
    }

    public Result edit(int artist){
        return ok("Estoy editando una cuenta");
    }

    public Result update(int artist){
        return ok("estoy actualizando una cuenta");
    }

    public Result delete(int artist){
        return ok("estoy borrando una cuenta");
    }
}
