package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.model.person.User;

public interface UserRepo extends CrudRepository<User, Long>{

	User findByUserName(String username);

}
