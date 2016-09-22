package pasman.model;

import pasman.bean.Group;
import pasman.bean.UserClient;
import pasman.dao.GroupDao;
import pasman.dao.UserDao;
import pasman.dto.UserDto;
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


    /**
     * Public registration method.
     *
     * @param mail user e-mail from client side
     * @param name user name from client side
     * @param password user password from client side
     * @return Response with user data as JSON
     */
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
        UserClient userClient = userDAOService.findByName(user.getUsername());
        UserDto userDto = new UserDto();
        userDto.setUserDtoFromUser(userClient);
        return Response.ok(userDto,MediaType.APPLICATION_JSON_TYPE).build();
    }
}
