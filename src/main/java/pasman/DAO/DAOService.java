package pasman.DAO;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public abstract class DAOService<T> {

    @PersistenceUnit
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV");//

    @PersistenceContext(unitName = "DEV")
    public EntityManager em = emf.createEntityManager();

    public void add(T car) {
        em.persist(car);
    }

    public void delete(Integer id, Class<T> typeParameterClass) {
        em.remove(get(id, typeParameterClass));
    }

    public T get(Integer id, Class<T> typeParameterClass) {
        return em.find(typeParameterClass, id);
    }


    public void update(T object) {
        em.persist(object);
    }


}