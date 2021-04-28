package persistence.dao;

import persistence.entity.Recept;
import persistence.util.Dao;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceptDao extends Dao<Recept, Long> {

    private static ReceptDao instance;

    private ReceptDao(EntityManager em) {
        super(em);
    }

    public static ReceptDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new ReceptDao(em);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public Map<Long, String> getReceptenNaamOpId() {
        Map<Long, String> output = new HashMap<>();
        List<Object[]> resultList = em.createQuery("SELECT r.id, r.titel FROM Recept r").getResultList();
        for (Object[] o : resultList) {
            output.put((Long) o[0], o[1].toString());
        }
        return output;
    }
}
