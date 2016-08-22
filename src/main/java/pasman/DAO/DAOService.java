package pasman.DAO;

import javax.ejb.Stateless;
import javax.persistence.*;
@Stateless
public abstract class DAOService<T> {

    @PersistenceUnit
    public static EntityManagerFactory emf;
    @PersistenceContext//(unitName = "DEV")
    public static EntityManager em;

    protected EntityManager getEM() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DEV");//
        }
        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public void add(T car) {
        getEM().persist(car);
    }

    public void delete(Integer id, Class<T> typeParameterClass) {
        getEM().remove(get(id, typeParameterClass));
    }

    public T get(Integer id, Class<T> typeParameterClass) {
        return getEM().find(typeParameterClass, id);
    }


    public void update(T object) {
        getEM().persist(object);
    }


}