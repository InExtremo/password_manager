package pasman.DAO;

import pasman.model.Data;
import pasman.model.User;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
@Stateless
public class UserDao extends DAOService<User> {
    public List<User> getAll() {
        TypedQuery<User> namedQuery = (TypedQuery<User>) em.createNamedQuery("User.getAll");
        return namedQuery.getResultList();
    }

    public boolean addData(Integer id, Data dataObj) {
        User user = get(id, User.class);
        if (user == null)
            return false;

        em.getTransaction().begin();
        Data data = new Data();
        data.setDescription(dataObj.getDescription());
        data.setPassword(dataObj.getPassword());
        data.setLink(dataObj.getLink());
        data.setLogin(dataObj.getLogin());
        data.setName(dataObj.getName());
        user.getData().add(data);
        update(user);
        em.getTransaction().commit();
        return true;
    }
}
