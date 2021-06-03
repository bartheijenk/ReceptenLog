package org.bartheijenk.persistence.dao;

import lombok.extern.log4j.Log4j2;
import org.bartheijenk.persistence.entity.User;
import org.bartheijenk.persistence.util.PasswordUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

@Log4j2

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserDao extends Dao<User, String> {
    public User authenticate(String login, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByLoginAndPassword", User.class);
        query.setParameter("login", login);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        User user = query.getSingleResult();

        if (user == null) throw new SecurityException("Invalid user/password");

        return user;
    }
}
