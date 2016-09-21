package pasman.dao;

import pasman.bean.Group;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
@Stateless
@LocalBean
public class GroupDao {

    @PersistenceUnit(unitName = "DEV")
    EntityManagerFactory DEV;

    @PersistenceContext(unitName = "DEV")
    EntityManager em;


    public void add(Group object) {
            em.persist(object);
    }

    public void delete(Integer id) {
            em.remove(get(id, Group.class));

    }

    public Group get(Integer id, Class<Group> typeParameterClass) {
        Group group = null;
            group = em.find(Group.class, id);
        return group;
    }

    public List<Group> getAll() {
        List<Group> groups = null;
            TypedQuery<Group> namedQuery = (TypedQuery<Group>) em.createNamedQuery("Group.getAll");
            groups = namedQuery.getResultList();

        return groups;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }
}
