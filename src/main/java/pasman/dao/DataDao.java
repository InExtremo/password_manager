package pasman.dao;

import pasman.bean.Data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */

public class DataDao {

    //TODO need add cryptography for data

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    public void add(Data object) {
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

    public Data get(Integer id) {
        EntityManager em = emf.createEntityManager();
        Data data = null;
        try {
            data = em.find(Data.class, id);
        } finally {
            em.close();
        }
        return data;
    }

    public List<Data> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Data> datas = null;
        try {
            TypedQuery<Data> namedQuery = (TypedQuery<Data>) em.createNamedQuery("Data.getAll");
            datas = namedQuery.getResultList();
        } finally {
            em.close();
        }
        return datas;
    }

    public Data update(Integer id, Data dataObj) {
        EntityManager em = emf.createEntityManager();
        Data data = null;
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            data = em.find(Data.class, id);
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            em.merge(data);
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
        return data;
    }

    public List<Data> getAllByUser(Integer userID) {
        EntityManager em = emf.createEntityManager();
        List<Data> datas = null;
        try {
            TypedQuery<Data> namedQuery =
                    (TypedQuery<Data>) em.createNamedQuery("Data.findDataByUser").setParameter("user", userID);
            datas = namedQuery.getResultList();
        } finally {
            em.close();
        }
        return datas;
    }

    public Data addData(Integer userID, Data dataObj) {
        EntityManager em = emf.createEntityManager();
        Data data = null;
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.joinTransaction();
            data = new Data();
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            data.setUserId(userID);
            em.persist(em.merge(data));
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
        return data;
    }

    public void deleteData(Integer userID, Integer dataId) throws NoPermissionException {
        EntityManager em = emf.createEntityManager();
        try {
            Data deletedData = get(dataId);
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.joinTransaction();
            if (deletedData.getUserId().equals(userID)) { //check user for permission for deleting data
                em.remove(em.merge(deletedData));
            } else {
                throw new NoPermissionException();
            }
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
}
