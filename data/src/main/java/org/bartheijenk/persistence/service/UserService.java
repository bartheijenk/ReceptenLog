package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.UserDao;
import org.bartheijenk.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        this.userDao.save(user);
        return user;
    }

    @Override
    public User authenticate(String login, String password) {
        return userDao.authenticate(login, password);
    }
}
