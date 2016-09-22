package pasman.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pasman.dao.DataDao;
import pasman.dao.GroupDao;
import pasman.dao.UserDao;
import pasman.bean.Data;
import pasman.bean.Group;
import pasman.bean.UserClient;
import pasman.dto.DataDto;
import pasman.dto.UserDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/*
 * Root resource (exposed at "myresource" path)
 */
@Stateless
@Path("/secure")
public class SecureResource {
    //TODO need add cryptography for data
    private static final Logger LOGGER = LoggerFactory.getLogger(SecureResource.class);

    @Inject
    DataDao dataDAOService;
    @Inject
    UserDao userDAOService;
    @Inject
    GroupDao groupDAOService;


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @param servletRequest - context param HttpServletRequest type
     * @return String that will be returned as a text/plain response.
     */
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataDto> getAll(@Context HttpServletRequest servletRequest) {
        LOGGER.info("getAll method call by " + servletRequest.getRemoteUser());
        List<DataDto> list = new ArrayList<>();
        dataDAOService.getAllByUser(userDAOService.getUserID(servletRequest)).forEach(v -> {
            DataDto data = new DataDto();
            data.setDataDtoFromData(v);
            list.add(data);
        });
        return list;
    }

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @param newData        data object from POST
     * @param servletRequest
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DataDto addData(DataDto newData, @Context HttpServletRequest servletRequest) {
        Data data = new Data();
        DataDto dataDto = new DataDto();
        data.setDataFromDTO(newData);
        dataDto.setDataDtoFromData(dataDAOService.addData(userDAOService.getUserID(servletRequest), data));
        return dataDto;
    }

    /**
     * Update user data by PUT method.
     *
     * @param id      Data id
     * @param newData new data for updating
     * @return updated data
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DataDto updateData(@PathParam("id") Integer id, DataDto newData) {
        LOGGER.info("UPDATE PUT -> id:" + id + " geted dataDto: " + newData);
        DataDto dataDto = new DataDto();
        Data data = new Data();

        data.setDataFromDTO(newData);
        dataDto.setDataDtoFromData(dataDAOService.update(id, data));

        return dataDto;
    }

    /**
     * Delete selected data by id.
     *
     * @param dataId         id from selected data
     * @param servletRequest HttpServletRequest for getting user id
     * @return Response with status of removing.
     */
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
     * JAX-RS Get method for getting current user from request context.
     *
     * @param servletRequest - context param HttpServletRequest type
     * @return UserDTO class object with user data.
     */
    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getCurrentUser(@Context HttpServletRequest servletRequest) {
        UserClient userClient;
        userClient = userDAOService.get(userDAOService.getUserID(servletRequest));
        UserDto userDto = new UserDto();
        userDto.setUserDtoFromUser(userClient);
        return userDto;
    }


}
