package pasman.model;

import pasman.bean.Group;
import pasman.bean.UserClient;
import pasman.dao.GroupDao;
import pasman.dao.UserDao;
import pasman.model.service.Cryptography;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Max on 28.08.2016.
 */
@Stateless
@Path("/public")
public class PublicApi {
    @Inject
    UserDao userDAOService;
    @Inject
    GroupDao groupDao;

    @Path("getUserData")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context HttpServletRequest servletRequest) {

        UserClient man = userDAOService.findByName(servletRequest.getRemoteUser());
        List<UserClient> userses = userDAOService.getAll();

        StringBuffer user_info = new StringBuffer();

        userses.forEach(user -> {
            user_info.append(user.getName() + " password: " + user.getPassword() + " username: " + user.getUsername() + "\n");
        });


        return user_info.toString() + " \n " + servletRequest.getRemoteUser() + "\n" + man.getId() + "\n" + man.getPassword();
    }

    @POST
    @Path("/parse")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(@FormParam("mail") String mail, @FormParam("name") String name, @FormParam("password") String password) {
        try {
            UserClient user = new UserClient();
            user.setUsername(mail);
            user.setName(name);
            user.setPassword(Cryptography.hash256(password));
            userDAOService.add(user);
            Group group = new Group();
            group.setGroupName("user");
            group.setUserid(mail);
            groupDao.add(group);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
