package pasman;

import pasman.POJO.Service;
import pasman.model.Data;
import pasman.model.Group;
import pasman.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        Service s = new Service<>(Data.class);
        List<Data> list = s.getAll("Data.getAll");

        StringBuffer text = new StringBuffer();
        list.forEach(u -> {
            text.append(u.getDescription()+" ");
        });
        Service<User> service2 = new Service<>(User.class);
        List<User> userses = service2.getAll("User.getAll");

        StringBuffer text2 = new StringBuffer();

        userses.forEach(users -> {
            text2.append(users.getName() + ": \n" );
            StringBuffer text3 = new StringBuffer();
            users.getData().forEach(data ->
                    text3.append("\t"+data.getLink()+"; \n"));
            text2.append(text3.toString());
        });

        StringBuffer gruptext = new StringBuffer();
        Service<Group> groupService = new Service<>(Group.class);

        List<Group> groups = groupService.getAll("Group.getAll");
        groups.forEach(group -> {
            gruptext.append(group.getGroupName() + " user:" + group.getUserid());
        });

        return text2.toString()+ "\n" +gruptext.toString();
    }
}
