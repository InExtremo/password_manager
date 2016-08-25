package pasman.DAO;

import pasman.POJOs.Data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */

public class DataDao {
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
    // @PersistenceUnit(unitName = "DEV")
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");

    //public EntityManager em = Persistence.createEntityManagerFactory("DEV").createEntityManager();
    public void add(Data object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id, Class<Data> typeParameterClass) {
        EntityManager em = emf.createEntityManager();
        try {
            em.remove(get(id, Data.class));
        } finally {
            em.close();
        }
    }

    public Data get(Integer id, Class<Data> typeParameterClass) {
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
            em.getTransaction().begin();
            data = em.find(Data.class, id);
            data.setDescription(dataObj.getDescription());
            data.setPassword(dataObj.getPassword());
            data.setLink(dataObj.getLink());
            data.setLogin(dataObj.getLogin());
            data.setName(dataObj.getName());
            em.merge(data);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return data;
    }
}
