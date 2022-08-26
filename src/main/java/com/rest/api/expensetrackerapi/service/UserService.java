package com.rest.api.expensetrackerapi.service;

import com.rest.api.expensetrackerapi.entity.User;
import com.rest.api.expensetrackerapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User read();

    User update(UserModel user);

    void delete();

    User getLoggedInUser();
}
