package pasman.jaas;

import pasman.model.service.Cryptography;
import pasman.bean.Group;
import pasman.bean.UserClient;
import services.IUserAuthenticationService;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 05.09.2016.
 */
@Stateless
@LocalBean
public class UserAuthenticationService implements IUserAuthenticationService {
    private
    @PersistenceContext(unitName = "DEV")
    EntityManager em;

    @Override
    public void validatePassword(String usid, String password) throws Exception {
        UserClient users = null;
        users = em.find(UserClient.class, 1);

        if (users == null) users = (UserClient) em.createQuery(
                "SELECT u FROM cleint u WHERE u.username = :userName")
                .setParameter("userName", usid).getResultList().get(0);
        //TODO add salt to password and fix login page
        String pass = Cryptography.hash256(password);
        if (users == null || !pass.equals(users.getPassword())) throw new Exception("Username or password not valid");
    }

    @Override
    public List<String> getGroups(String usid) {
        List<Group> groups = null;
        TypedQuery<Group> namedQuery = (TypedQuery<Group>) em.createNamedQuery("Group.getAll");
        groups = namedQuery.getResultList();
        ArrayList<String> strings = new ArrayList<>();
        groups.forEach(group -> strings.add(group.getGroupName()));
        return strings;
    }
}
