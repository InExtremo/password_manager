package pasman.DAO;

import pasman.POJOs.Data;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Max on 21.08.2016.
 */

public class DataDao extends DAOService<Data> {
    public List<Data> getAll() {
        TypedQuery<Data> namedQuery = (TypedQuery<Data>) getEM().createNamedQuery("Data.getAll");
        return namedQuery.getResultList();
    }
}
