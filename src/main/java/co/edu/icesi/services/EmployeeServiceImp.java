package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.repositories.EmployeeRepo;

@Service
public class EmployeeServiceImp implements EmployeeService {
	
	EmployeeRepo employeeRepo;
	
	public EmployeeServiceImp(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}
	
	@Override
	public void saveEmployee(Employee employee) {
		employeeRepo.save(employee);
	}

	@Override
	public void editEmployee(Employee employee) {
		
	}

	@Override
	public Optional<Employee> findEmployee(int id) {
		return employeeRepo.findById(id);
	}

	@Override
	public Iterable<Employee> findAll() {
		return employeeRepo.findAll();
	}

}
