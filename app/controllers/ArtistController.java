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

/**
 *This controller contains an action to handle HTTP requests and execute querys into the artist table.
 */
public class ArtistController extends Controller {
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
     * Shows the form to create an artist
     * @return Result with the render of the form to create an artist
     */
    public Result create() {
        final Form<Artist> form = formFactory.form(Artist.class);
        return ok(create_artist.render(form));
    }

    /**
     * Stores an artist into the database
     * @return Result with a redirect to show the artist created or a rejected form binded from Request
     */
    @Transactional
    public Result store() {
        EntityManager em = jpaApi.em();
        Form<Artist> form = formFactory.form(Artist.class).bindFromRequest();
        Artist nArtist = form.get();

        TypedQuery query = em.createQuery(
                "SELECT a FROM Artist a WHERE a.username = :username", Artist.class);
        try{
            Artist look =  (Artist)query.setParameter("username", nArtist.getUsername()).getSingleResult();
            form.reject("username","user already exist");
            return badRequest(create_artist.render(form));
        }catch(NoResultException e){
            Integer id = em.createNamedQuery("Artist.maxId",Integer.class)
                    .getSingleResult();
            if(id==null) {
                id=0;
            }
            nArtist.setId(++id);
            em.persist(nArtist);
            return redirect(controllers.routes.ArtistController.show(nArtist.getId()));
        }

    }

    /**
     * Shows the view of an artist
     * @param artist_id the id of the artist to show
     * @return Response with the view of the artist needed, or a bad request
     */
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

    /**
     * Shows the form to edit an artist
     * @param artist_id the id of the artist to edit
     * @return Response with a form to edit or a bad request
     */
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

    /**
     * Updates the artist with the id given
     * @param artist_id the id of the artist to update
     * @return Response that goes to the artist edited or a bad request with the form binded from Request rejected
     */
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

    /**
     * Deletes the artist with the id given
     * @param artist_id the id of the artist to delete
     * @return Response that goes to the artist index or a bad request
     */
    @Transactional
    public Result delete(int artist_id){
        EntityManager em = jpaApi.em();
        Artist artist = em.find(Artist.class, artist_id);
        if(artist!=null){
           em.remove(artist);
           return redirect(controllers.routes.ArtistController.index());
        }else{
            return badRequest("artist does not exist");
        }
    }

    /**
     * Shows all the artists
     * @return Response with the view that shows the artists
     */
    @Transactional(readOnly=true)
    public Result index() {
        EntityManager em = jpaApi.em();
        Query query = em.createQuery("SELECT e FROM Artist e");
        return ok(views.html.index.render((List<Artist>) query.getResultList()));
    }

}
