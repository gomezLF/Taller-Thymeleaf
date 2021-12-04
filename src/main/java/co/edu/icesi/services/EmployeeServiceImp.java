package co.edu.icesi.services;

import java.time.LocalDate;
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
		Optional<Employee> temp = findEmployee(employee.getBusinessentityid());
		
		if(temp.isPresent()) {
			temp.get().setBusinessentityid(employee.getBusinessentityid());
			temp.get().setBirthdate(employee.getBirthdate());
			temp.get().setGender(employee.getGender());
			temp.get().setHiredate(employee.getHiredate());
			temp.get().setModifieddate(LocalDate.now());
			
			employeeRepo.save(temp.get());
		}
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
