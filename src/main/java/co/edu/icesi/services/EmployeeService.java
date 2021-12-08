package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.model.hr.Employee;

@Service
public interface EmployeeService {
	void saveEmployee(Employee employee);
	void editEmployee(Integer id, Employee employee);
	Optional<Employee> findEmployee(int id);
	Iterable<Employee> findAll();
}
