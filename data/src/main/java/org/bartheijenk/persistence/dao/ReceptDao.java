package org.bartheijenk.persistence.dao;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.entity.Tag;

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

    public Map<Long, String> getRandomRecepten(int limit, List<Tag> tags) {
        //MySQL specific query!!!!
        StringBuilder s = new StringBuilder("SELECT r.id, r.titel FROM recipelog.recept r " +
                "WHERE r.id in (" +
                "SELECT rt.recept_id FROM recipelog.recept_tag rt where");
        for (int i = 0; i < tags.size(); i++) {
            if (i == 0)
                s.append(" rt.tag_id = ").append(tags.get(i).getId().toString());
            else
                s.append(" or rt.tag_id = ").append(tags.get(i).getId().toString());
        }

        s.append(") order by RAND() LIMIT ").append(limit);
        Query query = em.createNativeQuery(s.toString());

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