package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.User;

public interface IUserService {
    User addUser(User user);

    User authenticate(String login, String password);

}
