package pasman.dao;

import pasman.bean.Data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */
@Stateless
@LocalBean
public class DataDao {// implements IDAO<Data>

    @PersistenceUnit(unitName = "DEV")
    EntityManagerFactory DEV;

    @PersistenceContext(unitName = "DEV")
    EntityManager em;

    public void add(Data object) {
        em.persist(object);
    }

    public void delete(Integer id) {
        em.remove(get(id));
    }

    public Data get(Integer id) {
        Data data = null;
        data = em.find(Data.class, id);
        return data;
    }

    public List<Data> getAll() {
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
        Data data = null;
        data = em.find(Data.class, id);
        data.setDescription(dataObj.getDescription());
        data.setPassword(dataObj.getPassword());
        data.setLink(dataObj.getLink());
        data.setLogin(dataObj.getLogin());
        data.setName(dataObj.getName());
        em.merge(data);
        return data;
    }

    public List<Data> getAllByUser(Integer userID) {
        List<Data> datas = null;
        TypedQuery<Data> namedQuery =
                (TypedQuery<Data>) em.createNamedQuery("Data.findDataByUser").setParameter("user", userID);
        datas = namedQuery.getResultList();
        return datas;
    }

    public Data addData(Integer userID, Data dataObj) {
        Data data = null;
        em.joinTransaction();
        data = new Data();
        data.setDescription(dataObj.getDescription());
        data.setPassword(dataObj.getPassword());
        data.setLink(dataObj.getLink());
        data.setLogin(dataObj.getLogin());
        data.setName(dataObj.getName());
        data.setUserId(userID);
        em.persist(em.merge(data));
        return data;
    }

    public void deleteData(Integer userID, Integer dataId) {
        Data deletedData = get(dataId);
        if (deletedData.getUserId().equals(userID)) { //check user for permission for deleting data
            em.remove(em.merge(deletedData));
        } else {
            //TODO some work
        }
    }
}
