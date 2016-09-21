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
import javax.ws.rs.core.Response;
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

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam("mail") String mail, @FormParam("name") String name, @FormParam("password") String password) {
        UserClient user=null;
        try {
            user = new UserClient();
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
        return Response.ok(user,MediaType.APPLICATION_JSON_TYPE).build();
    }
}
