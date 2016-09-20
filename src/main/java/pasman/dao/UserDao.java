package pasman.dao;

import pasman.bean.UserClient;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
public class UserDao {
    //TODO need add cryptography for data

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    // public EntityManager em = Persistence.createEntityManagerFactory("DEV").createEntityManager();
    public void add(UserClient object) {
        EntityManager em = emf.createEntityManager();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.persist(object);
            transaction.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.remove(get(id));
            transaction.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
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
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.joinTransaction();
            em.merge(userClient);
            transaction.commit();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public UserClient findByName(String name) {
        EntityManager em = emf.createEntityManager();
        UserClient userClient = null;
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            userClient = (UserClient) em.createQuery(
                    "SELECT u FROM cleint u WHERE u.username = :userName")
                    .setParameter("userName", name)
                    .setMaxResults(1)
                    .getSingleResult();
            transaction.commit();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
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

}
