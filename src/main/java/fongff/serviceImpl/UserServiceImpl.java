package fongff.serviceImpl;


import fongff.model.User;
import fongff.repository.UserRepository;
import fongff.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	public boolean isUserExist(String userName) {
		User user = new User();
		user = userRepository.findByName(userName);
		return user == null ? false : true;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUser(String name) {
		User user = new User();
		Optional<User> opt = userRepository.findById(name);
		if (opt.isPresent()) {
			user = opt.get();
		}
		return user;
	}

	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

}
