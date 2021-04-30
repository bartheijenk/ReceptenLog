package org.bartheijenk.persistence.dao;

import lombok.extern.log4j.Log4j2;
import org.bartheijenk.persistence.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Tag> findAllByNaam(List<String> tagNames) {
        TypedQuery<Tag> query = em.createQuery("select t from Tag t where t.naam in :tagNames", Tag.class);
        query.setParameter("tagNames", tagNames);
        return query.getResultList();
    }

    public List<Tag> findAllById(List<Long> tagIds) {
        TypedQuery<Tag> query = em.createQuery("select t from Tag t where t.id in :tagIds", Tag.class);
        query.setParameter("tagIds", tagIds);
        return query.getResultList();
    }
}
