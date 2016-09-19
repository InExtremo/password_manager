package pasman.DAO;

import pasman.POJOs.Data;

import javax.naming.NoPermissionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.security.Permission;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */

public class DataDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    public void add(Data object) {
        EntityManager em = emf.createEntityManager();
        try {
//            em.getTransaction().begin();
            em.persist(object);
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.remove(get(id));
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
//            em.getTransaction().begin();
            data = em.find(Data.class, id);
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            em.merge(data);
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return data;
    }

    public  List<Data> getAllByUser(Integer userID){
        EntityManager em = emf.createEntityManager();
        List<Data> datas = null;
        try {
            TypedQuery<Data> namedQuery =
                    (TypedQuery<Data>) em.createNamedQuery("Data.findDataByUser").setParameter("user",userID);
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
//            em.getTransaction().begin();
            data = new Data();
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            data.setUserId(userID);
            em.persist(data);
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return data;
    }

    public void deleteData(Integer userID, Integer dataId) throws NoPermissionException {
        EntityManager em = emf.createEntityManager();
        try {
//            em.getTransaction().begin();
            Data deletedData = em.find(Data.class, dataId);
            if(deletedData.getUserId().equals(userID)){ //check user for permission for deleting data
                em.remove(em.merge(deletedData));
            }else {
                throw new NoPermissionException();
            }
//            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
