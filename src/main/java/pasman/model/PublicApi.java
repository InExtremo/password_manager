package pasman.model;

import pasman.DAO.UserDao;
import pasman.POJOs.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Max on 28.08.2016.
 */
@Path("/public")
public class PublicApi {

    UserDao userDAOService = new UserDao();

    @Path("getSomeTrash")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context HttpServletRequest servletRequest) {

        List<User> userses = userDAOService.getAll();

        StringBuffer user_info = new StringBuffer();

        userses.forEach(user -> {
            user_info.append(user.getName() + " password: " + user.getPassword() + " username: " + user.getUsername() + "\n");
        });


        return user_info.toString();
    }
}
