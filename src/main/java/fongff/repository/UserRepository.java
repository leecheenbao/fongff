package fongff.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fongff.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);
}
