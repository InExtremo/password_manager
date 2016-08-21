package pasman.DAO;

import pasman.model.Group;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
public class GroupDao extends DAOService<Group> {
    public List<Group> getAll() {
        TypedQuery<Group> namedQuery = (TypedQuery<Group>) em.createNamedQuery("Group.getAll");
        return namedQuery.getResultList();
    }
}
