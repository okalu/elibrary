package cs489.apsd.elibrary.service;

import cs489.apsd.elibrary.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User getUserById(Integer userId);
}
