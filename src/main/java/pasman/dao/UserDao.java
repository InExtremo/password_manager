package pasman.dao;

import pasman.bean.UserClient;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
@Stateless
@LocalBean
public class UserDao {
    //TODO need add cryptography for data

    @PersistenceUnit(unitName = "DEV")
    EntityManagerFactory emf;

    @PersistenceContext(unitName = "DEV")
    EntityManager em;

    public void add(UserClient object) {
        em.persist(object);
    }

    public void delete(Integer id) {
        em.remove(get(id));
    }

    public UserClient get(Integer id) {
        UserClient userClient = null;
        userClient = em.find(UserClient.class, id);
        return userClient;
    }

    public List<UserClient> getAll() {
        List<UserClient> userClients = null;
        TypedQuery<UserClient> namedQuery = (TypedQuery<UserClient>) em.createNamedQuery("User.getAll");
        userClients = namedQuery.getResultList();
        return userClients;
    }

    public void update(UserClient userClient) {
        em.merge(userClient);
    }


    public UserClient findByName(String name) {
        UserClient userClient = null;
        userClient = (UserClient) em.createQuery(
                "SELECT u FROM cleint u WHERE u.username = :userName")
                .setParameter("userName", name)
                .setMaxResults(1)
                .getSingleResult();
        return userClient;
    }

    /**
     * Getting user id from session by user name. This method also cache id to session,
     * if session contains user id then return, else find it in DB.
     *
     * @param rq - HttpServletRequest request for getting current session
     * @return String with user ID
     * @see #findByName(String)
     */
    public Integer getUserID(HttpServletRequest rq) {
        UserClient userClient;
        HttpSession session = rq.getSession();
        if (session.getAttribute("id") == null) {
            userClient = findByName(rq.getRemoteUser());
            session.setAttribute("id", userClient.getId());
        } else {
            userClient = get((Integer) session.getAttribute("id"));
        }
        return userClient.getId();
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

}
