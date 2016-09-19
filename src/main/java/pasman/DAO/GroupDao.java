package pasman.DAO;

import pasman.POJOs.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */

public class GroupDao {
    //TODO need add cryptography for data
    //    @PersistenceUnit
//    public static EntityManagerFactory emf;
//    @PersistenceContext//(unitName = "DEV")
//    public static EntityManager em;
//
//    protected EntityManager getEM() {
//        if (emf == null) {
//            emf = Persistence.createEntityManagerFactory("DEV");//
//        }
//        if (em == null) {
//            em = emf.createEntityManager();
//        }
//        return em;
//    }
    //@PersistenceUnit(unitName = "DEV")
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    public void add(Group object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(get(id, Group.class));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Group get(Integer id, Class<Group> typeParameterClass) {
        EntityManager em = emf.createEntityManager();
        Group group = null;
        try {
            em.getTransaction().begin();
            group = em.find(Group.class, id);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return group;
    }

    public List<Group> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Group> groups = null;
        try {
            TypedQuery<Group> namedQuery = (TypedQuery<Group>) em.createNamedQuery("Group.getAll");
            groups = namedQuery.getResultList();
        } finally {
            em.close();
        }
        return groups;
    }
}
