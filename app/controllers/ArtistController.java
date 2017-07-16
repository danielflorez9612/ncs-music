package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import javax.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import javax.persistence.*;
import play.db.jpa.JPAApi;
import java.util.*;

public class ArtistController extends Controller {


    @Inject
    FormFactory formFactory;

    @Inject
    JPAApi jpaApi;

    public Result create() {
        final Form<Artist> form = formFactory.form(Artist.class);
        return ok(create_artist.render(form));
    }

    @Transactional
    public Result store() {
        EntityManager em = jpaApi.em();
        Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        Artist nArtist = form.get();

        TypedQuery<Artist> query = em.createQuery(
                "SELECT a FROM Artist a WHERE a.username = :username", Artist.class);
        Artist look =  query.setParameter("username", nArtist.getUsername()).getSingleResult();
        if(look!=null){
            form.reject("username","user already exist");
            return badRequest(create_artist.render(form));
        }
        Integer id = em.createNamedQuery("Artist.maxId",Integer.class)
                .getSingleResult();
        if(id==null) {
            id=0;
        }
        nArtist.setId(++id);
        em.persist(nArtist);
        return redirect(controllers.routes.ArtistController.show(nArtist.getId()));
    }

    @Transactional(readOnly=true)
    public Result show(int artist_id) {
        Artist artist = JPA.em().find(Artist.class,artist_id);
        if(artist!=null){
            final Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
            return ok(view_artist.render(artist));
        }else{
            return badRequest("artist does not exist");
        }
    }
    @Transactional(readOnly=true)
    public Result edit(int artist_id){
        EntityManager em = jpaApi.em();
        Artist artist = em.find(Artist.class,artist_id);
        if(artist==null){
            return badRequest("Artist does not exist");
        }else{
            final Form<Artist> form = formFactory.form(Artist.class).fill(artist);
            return ok(update_artist.render(form,artist));
        }
    }
    @Transactional
    public Result update(int artist_id){
        EntityManager em = jpaApi.em();
        Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        java.util.Map<String,String> hm = form.data();
        try{
            Artist artist = em.find(Artist.class,artist_id);
            artist.setUsername(hm.get("username"));
            artist.setForename(hm.get("forename"));
            artist.setSurname(hm.get("surname"));
            return redirect(controllers.routes.ArtistController.show(artist.getId()));
        }catch(Exception e){
            form.reject("username","user already exist");
            return badRequest(create_artist.render(form));
        }

    }

    @Transactional
    public Result delete(int artist_id){
        EntityManager em = jpaApi.em();
        Artist artist = em.find(Artist.class, artist_id);
        if(artist!=null){
           em.remove(artist);
           return ok("Artist deleted succesfully");
        }else{
            return ok("artist does not exist");
        }
    }

    @Transactional(readOnly=true)
    public Result index() {
        EntityManager em = jpaApi.em();
        Query query = em.createQuery("SELECT e FROM Artist e");
        return ok(views.html.index.render((List<Artist>) query.getResultList()));
    }

}
