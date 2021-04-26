package persistence.dao;

import lombok.extern.log4j.Log4j2;
import persistence.entity.Tag;
import persistence.util.Dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Log4j2
public class TagDao extends Dao<Tag, Long> {

    private static TagDao instance;

    private TagDao(EntityManager em) {
        super(em);
    }

    public static TagDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new TagDao(em);
        }
        return instance;
    }

    public void saveIfNotExists(Tag tag) {
        if (findByNaam(tag.getNaam()) == null) {
            save(tag);
        }
    }

    public Tag findByNaam(String naam) {
        TypedQuery<Tag> query = em.createQuery("select i from Tag i where i.naam=:naam", Tag.class);
        query.setParameter("naam", naam);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
