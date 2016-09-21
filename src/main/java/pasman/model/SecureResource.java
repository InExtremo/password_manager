package pasman.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pasman.dao.DataDao;
import pasman.dao.GroupDao;
import pasman.dao.UserDao;
import pasman.bean.Data;
import pasman.bean.Group;
import pasman.bean.UserClient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/*
 * Root resource (exposed at "myresource" path)
 */
@Stateless
@Path("/secure")
public class SecureResource {
    //TODO need add cryptography for data
    private static final Logger logger = LoggerFactory.getLogger(SecureResource.class);

    @Inject
    DataDao dataDAOService;
    @Inject
    UserDao userDAOService;
    @Inject
    GroupDao groupDAOService;

    @Path("db")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context HttpServletRequest servletRequest) {
        List<Data> list = dataDAOService.getAll();
        List<UserClient> userses = userDAOService.getAll();
        List<Group> groups = groupDAOService.getAll();
        StringBuffer text = new StringBuffer();
        StringBuffer gruptext = new StringBuffer();
        groups.forEach(group -> gruptext.append(group.getGroupName() + " user:" + group.getUserid()));
        userses.forEach(user -> {
            text.append("\nName: " + user.getName() + " with password: " + user.getPassword() + "\n\t");
            list.forEach(data -> {
                if (data.getUserId().equals(user.getId()))
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
    public Response deleteData(@PathParam("id") Integer dataId, @Context HttpServletRequest servletRequest) {
        int id = userDAOService.getUserID(servletRequest);
        Data data = dataDAOService.get(dataId);
        if (data.getUserId().equals(id)) {
            dataDAOService.deleteData(dataId);
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("You have not access to this data").build();
        }
        return Response.ok().build();
    }


    /**
     * JAX-RS Get method for getting user from request context.
     *
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
