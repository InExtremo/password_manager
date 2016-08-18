package pasman.POJO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Service<T> {
    final Class<T> typeParameterClass;

    public Service(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public EntityManager em = Persistence.createEntityManagerFactory("DEV").createEntityManager();

    public T add(T car) {
        em.getTransaction().begin();
        T carFromDB = em.merge(car);
        em.getTransaction().commit();
        return carFromDB;
    }

    public void delete(long id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public T get(long id) {
        return em.find(typeParameterClass, id);
    }

    public void update(T car) {
        em.getTransaction().begin();
        em.merge(car);
        em.getTransaction().commit();
    }

    public List<T> getAll(String q) {
        TypedQuery<T> namedQuery = (TypedQuery<T>) em.createNamedQuery(q);
        return namedQuery.getResultList();
    }

}