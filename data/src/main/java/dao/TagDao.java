package dao;

import entity.Tag;
import util.Dao;

import javax.persistence.EntityManager;

public class TagDao extends Dao<Tag, Long> {

    private static TagDao instance;

    private TagDao(EntityManager em) {
        super(em);
    }

    public TagDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new TagDao(em);
        }
        return instance;
    }
}
