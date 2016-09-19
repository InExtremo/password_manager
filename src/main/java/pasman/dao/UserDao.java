package pasman.dao;

import pasman.pojos.UserClient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
public class UserDao {
    //TODO need add cryptography for data
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
    public void add(UserClient object) {
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
            em.remove(get(id));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public UserClient get(Integer id) {
        EntityManager em = emf.createEntityManager();
        UserClient userClient = null;
        try {
            userClient = em.find(UserClient.class, id);
        } finally {
            em.close();
        }
        return userClient;
    }

    public List<UserClient> getAll() {
        EntityManager em = emf.createEntityManager();
        List<UserClient> userClients = null;
        try {
            TypedQuery<UserClient> namedQuery = (TypedQuery<UserClient>) em.createNamedQuery("User.getAll");
            userClients = namedQuery.getResultList();
        } finally {
            em.close();
        }
        return userClients;
    }

    public void update(UserClient userClient) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(userClient);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }



    public UserClient findByName(String name) {
        EntityManager em = emf.createEntityManager();
        UserClient userClient = null;
        try {
//            em.getTransaction().begin();
            userClient = (UserClient) em.createQuery(
                    "SELECT u FROM cleint u WHERE u.username = :userName")
                    .setParameter("userName", name)
                    .setMaxResults(1)
                    .getSingleResult();
       //     em.getTransaction().commit();
        } finally {
            em.close();
        }
        return userClient;
    }

    /**
     * Getting user id from session by user name. This method also cache id to session,
     * if session contains user id then return, else find it in DB.
     * @see #findByName(String)
     * @param rq - HttpServletRequest request for getting current session
     * @return String with user ID
     */
    public  Integer getUserID(HttpServletRequest rq){
        UserClient userClient;
        HttpSession session = rq.getSession();
        if(session.getAttribute("id")==null){
            userClient = findByName(rq.getRemoteUser());
            session.setAttribute("id", userClient.getId());
        } else {
            userClient = get((Integer) session.getAttribute("id"));
        }
        return userClient.getId();
    }

}
