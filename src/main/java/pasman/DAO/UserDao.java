package pasman.DAO;

import pasman.POJOs.Data;
import pasman.POJOs.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
public class UserDao {
//    @PersistenceUnit
//    public static EntityManagerFactory emf;
//    @PersistenceContext//(unitName = "DEV")
//    public static EntityManager em;

    /*protected EntityManager getEM() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DEV");//
        }
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }*/
    //  @PersistenceUnit(unitName = "DEV")
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    // public EntityManager em = Persistence.createEntityManagerFactory("DEV").createEntityManager();
    public void add(User object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id, Class<User> typeParameterClass) {
        EntityManager em = emf.createEntityManager();
        try {
            em.remove(get(id, User.class));
        } finally {
            em.close();
        }
    }

    public User get(Integer id, Class<User> typeParameterClass) {
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, id);
        } finally {
            em.close();
        }
        return user;
    }

    public List<User> getAll() {
        EntityManager em = emf.createEntityManager();
        List<User> users = null;
        try {
            TypedQuery<User> namedQuery = (TypedQuery<User>) em.createNamedQuery("User.getAll");
            users = namedQuery.getResultList();
        } finally {
            em.close();
        }
        return users;
    }

    public void update(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Data addData(Integer id, Data dataObj) {
        EntityManager em = emf.createEntityManager();
        Data data = null;
        User user = get(id, User.class);
        if (user == null)
            return null;
        try {
            em.getTransaction().begin();
            data = new Data();
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            user.getData().add(data);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return data;
    }
}
