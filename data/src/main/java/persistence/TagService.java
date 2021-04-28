package persistence;

import persistence.dao.TagDao;
import persistence.entity.Tag;

import javax.persistence.EntityManager;
import java.util.List;

public class TagService {
    private static TagService instance;

    public static TagService getInstance() {
        if (instance == null) {
            instance = new TagService();
        }
        return instance;
    }

    private TagDao tagDao = TagDao.getInstance(EntityManagerProvider.getEntityManager());

    private TagService() {

    }

    private TagService(EntityManager em) {
        tagDao = TagDao.getInstance(em);
    }

    /**
     * Gives a list of all tags
     *
     * @return a List of Tag objects
     */
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    /**
     * Gives a Tag object with specified ID
     *
     * @param id the ID in Long
     * @return A Tag object or null
     */
    public Tag getTagById(Long id) {
        return tagDao.find(id);
    }
}
