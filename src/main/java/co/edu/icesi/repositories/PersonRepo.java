package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.person.Person;

@Repository
public interface PersonRepo extends CrudRepository<Person, Integer>{

}
