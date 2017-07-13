package controllers;

import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class AccountController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result create() {
        return ok("estoy creando una cuenta");
    }

    public Result store() {
        return ok("estoy guardando una cuenta");
    }

    public Result show(String username) {
        return ok("estoy mostrando una cuenta");
    }

    public Result edit(String username){
        return ok("Estoy editando una cuenta");
    }

    public Result update(String username){
        return ok("estoy actualizando una cuenta");
    }

    public Result delete(String usernme){
        return ok("estoy borrando una cuenta");
    }
}
