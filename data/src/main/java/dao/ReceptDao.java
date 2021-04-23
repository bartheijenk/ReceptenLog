package dao;

import entity.Recept;
import util.Dao;

import javax.persistence.EntityManager;

public class ReceptDao extends Dao<Recept, Long> {

    private static ReceptDao instance;

    private ReceptDao(EntityManager em) {
        super(em);
    }

    public ReceptDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new ReceptDao(em);
        }
        return instance;
    }
}
