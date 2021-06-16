package org.bartheijenk.persistence.dao;

import lombok.extern.log4j.Log4j2;
import org.bartheijenk.persistence.entity.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class Dao<E extends Identifiable<K>, K> {

    @PersistenceContext(unitName = "MySQL-recipelog")
    protected EntityManager em;


    public List<E> findAll() {
        return em.createNamedQuery(typeSimple() + ".findAll", E()).getResultList();
    }

    public List<E> findAllById(List<K> ids) {
        if (ids.isEmpty()) {
            return null;
        } else {
            TypedQuery<E> query = em.createNamedQuery(typeSimple() + ".findAllByIds", E());
            query.setParameter("ids", ids);
            return query.getResultList();
        }
    }

    public Optional<E> find(K id) {
        return Optional.ofNullable(em.find(E(), id));
    }

    public E save(E e) {
        em.persist(e);
        return e;
    }

    public E update(E e) {
        return em.merge(e);
    }

    public void remove(E e) {
        e = em.merge(e);
        em.remove(e);
    }

    public Boolean contains(E e) {
        return em.contains(e);
    }

    @SuppressWarnings("unchecked")
    private Class<E> E() {
        ParameterizedType thisDaoClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) thisDaoClass.getActualTypeArguments()[0];
    }

    private String typeSimple() {
        return E().getSimpleName();
    }
}
