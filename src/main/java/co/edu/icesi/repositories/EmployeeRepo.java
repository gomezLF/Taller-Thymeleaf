package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.hr.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Integer>{

}
