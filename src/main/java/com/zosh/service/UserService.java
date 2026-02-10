package com.zosh.service;

import com.zosh.exceptions.UserException;
import com.zosh.modal.User;

import java.util.List;

public interface UserService {
    User getUserFromJwtToke(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(Long id) throws UserException, Exception;
    List<User> getAllUser();
}
