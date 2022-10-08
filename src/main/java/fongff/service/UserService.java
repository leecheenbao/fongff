package fongff.service;

import java.util.List;

import fongff.model.User;

public interface UserService {

	List<User> findAllUsers();

	User findUser(String UserId);

	void addUser(User user);
}
