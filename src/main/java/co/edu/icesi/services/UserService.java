package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.model.person.User;

@Service
public interface UserService {
	void save(User user);
	Iterable<User> saveAll(Iterable<User> insts);
	Optional<User> findById(long id);
	Iterable<User> findAll();
	void updateUser(User user);
}
