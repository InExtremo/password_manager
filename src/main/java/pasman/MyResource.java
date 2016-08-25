package pasman;

import pasman.DAO.DataDao;
import pasman.DAO.GroupDao;
import pasman.DAO.UserDao;
import pasman.POJOs.Data;
import pasman.POJOs.Group;
import pasman.POJOs.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    DataDao dataDAOService = new DataDao();

    UserDao userDAOService = new UserDao();

    GroupDao groupDAOService = new GroupDao();

    @Path("getSomeTrash")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@Context HttpServletRequest servletRequest) {
        List<Data> list = dataDAOService.getAll();

        StringBuffer text = new StringBuffer();
        list.forEach(u -> {
            text.append(u.getDescription() + " ");
        });

        List<User> userses = userDAOService.getAll();

        StringBuffer text2 = new StringBuffer();

        userses.forEach(users -> {
            text2.append(users.getName() + ": \n");
            StringBuffer text3 = new StringBuffer();
            users.getData().forEach(data ->
                    text3.append("\t" + data.getLink() + "; \n"));
            text2.append(text3.toString());
        });

        StringBuffer gruptext = new StringBuffer();


        List<Group> groups = groupDAOService.getAll();
        groups.forEach(group -> {
            gruptext.append(group.getGroupName() + " user:" + group.getUserid());
        });


        return text2.toString() + "\n" + gruptext.toString() + "   " + servletRequest.getRemoteUser();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getAll(@Context HttpServletRequest servletRequest) {
        User user = userDAOService.findWithName(servletRequest.getRemoteUser());
        return user.getData();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Data addData(Data newData, @Context HttpServletRequest servletRequest) {
        User user = userDAOService.findWithName(servletRequest.getRemoteUser());
        return userDAOService.addData(user.getId(), newData);
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
    public void deleteData(@PathParam("id") Integer id, @Context HttpServletRequest servletRequest) {
        User user = userDAOService.findWithName(servletRequest.getRemoteUser());
        userDAOService.deleteData(user.getId(), id);
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser(@Context HttpServletRequest servletRequest) {
        User user = userDAOService.findWithName(servletRequest.getRemoteUser());
        return user;
    }

}
