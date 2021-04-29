package persistence.dao;

import persistence.entity.Recept;
import persistence.entity.Tag;
import persistence.util.Dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
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
        return queryToMap(em.createQuery("SELECT r.id, r.titel FROM Recept r"));

    }

    public Map<Long, String> getReceptenNaamOpIdPerTag(Tag tag) {
        Query query = em.createQuery("SELECT r.id, r.titel FROM Recept r where :tag member of r.tags");
        query.setParameter("tag", tag);
        return queryToMap(query);
    }

    public Map<Long, String> getRandomRecepten(int limit) {
        //MySQL specific query!!!!
        Query query = em.createNativeQuery("SELECT r.id, r.titel FROM recipelog.recept r order by " +
                "RAND() LIMIT :limit");
        query.setParameter("limit", limit);
        return queryToMap(query);
    }

    @SuppressWarnings("unchecked")
    private Map<Long, String> queryToMap(Query query) {
        Map<Long, String> output = new HashMap<>();

        List<Object[]> resultList = query.getResultList();
        for (Object[] o : resultList) {
            output.put(
                    o[0] instanceof BigInteger ? ((BigInteger) o[0]).longValue() : (Long) o[0],
                    o[1].toString());
        }
        return output;
    }
}
