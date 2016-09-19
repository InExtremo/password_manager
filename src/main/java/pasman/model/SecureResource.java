package pasman.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pasman.DAO.DataDao;
import pasman.DAO.GroupDao;
import pasman.DAO.UserDao;
import pasman.POJOs.Data;
import pasman.POJOs.Group;
import pasman.POJOs.UserClient;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/secure")
public class SecureResource {
    private static final Logger logger = LoggerFactory.getLogger(SecureResource.class);


    DataDao dataDAOService = new DataDao();

    UserDao userDAOService = new UserDao();

    GroupDao groupDAOService = new GroupDao();

    @Path("readDB")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context HttpServletRequest servletRequest) {

        List<Data> list = dataDAOService.getAll();
        List<UserClient> userses = userDAOService.getAll();
        List<Group> groups = groupDAOService.getAll();

        StringBuffer text = new StringBuffer();
        StringBuffer gruptext = new StringBuffer();

        groups.forEach(group -> {
            gruptext.append(group.getGroupName() + " user:" + group.getUserid());
        });

        userses.forEach(user -> {
            text.append("\nName: "+user.getName() + " with password: " + user.getPassword() + "\n\t");
            list.forEach(data -> {
                if(data.getUserId().equals(user.getId()))
                text.append("\n data: \t Name:" + data.getName() + " \t Link:" + data.getLink());
            });
        });

        return text.toString() + " \n Groups: \n\t" + gruptext.toString();
    }


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @param servletRequest - context param HttpServletRequest type
     * @return String that will be returned as a text/plain response.
     */
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getAll(@Context HttpServletRequest servletRequest) {
        logger.info("getAll method call by " + servletRequest.getRemoteUser());

        return dataDAOService.getAllByUser(userDAOService.getUserID(servletRequest));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Data addData(Data newData, @Context HttpServletRequest servletRequest) {
        return dataDAOService.addData(userDAOService.getUserID(servletRequest), newData);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Data updateData(@PathParam("id") Integer id, Data newData) {
        return dataDAOService.update(id, newData);
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteData(@PathParam("id") Integer dataId, @Context HttpServletRequest servletRequest) {
        try {
            dataDAOService.deleteData(userDAOService.getUserID(servletRequest), dataId);
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
    }


    /**
     * JAX-RS Get method for getting user from request context     *
     * @param servletRequest - context param HttpServletRequest type
     * @return UserClient class object with user data.
     */
    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserClient getCurrentUser(@Context HttpServletRequest servletRequest) {
        UserClient userClient;
        userClient = userDAOService.get(userDAOService.getUserID(servletRequest));
        return userClient;
    }


}
