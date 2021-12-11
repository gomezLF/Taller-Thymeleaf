package co.edu.icesi.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.repositories.SalespersonRepo;
import co.edu.icesi.services.EmployeeServiceImp;

@Controller
@RequestMapping("/employee")
public class EmployeeControllerImp implements EmployeeController{
	
	EmployeeServiceImp employeeServiceImp;
	SalespersonRepo salespersonRepo;
	
	
	public EmployeeControllerImp(EmployeeServiceImp employeeServiceImp, SalespersonRepo salespersonRepo) {
		this.employeeServiceImp = employeeServiceImp;
		this.salespersonRepo = salespersonRepo;
	}

	@Override
	@GetMapping("/add")
	public String addEmployee(Model model) {
		model.addAttribute("salespersons", salespersonRepo.findAll());
		model.addAttribute("types", EmployeeGender.values());
		model.addAttribute("employee", new Employee());
		return "/employee/add-employee";
	}
	
	@Override
	@PostMapping("/add")
	public String saveEmployee(@RequestParam(value = "action") String action, @ModelAttribute("employee") @Validated Employee employee, BindingResult result, Model model) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("employee", employee);
			model.addAttribute("types", EmployeeGender.values());
			model.addAttribute("salespersons", salespersonRepo.findAll());
			
			return "/employee/add-employee";
		}
		
		if(action != null && action.equals("Add")) {
			employeeServiceImp.saveEmployee(employee);
			model.addAttribute("employees", employeeServiceImp.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/employee/";
		}
		
		return "redirect:/employee/";
	}
	
	@Override
	@GetMapping
	public String indexEmployee(Model model) {
		model.addAttribute("employees", employeeServiceImp.findAll());
		return "employee/index";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateEmployee(@PathVariable("id") Integer id, Model model) {
		Optional<Employee> employee = employeeServiceImp.findEmployee(id);
		
		if(employee.isEmpty()) {
			new IllegalArgumentException("Invalid soh Id:" + id);

		}else {
			model.addAttribute("employee", employee.get());
			model.addAttribute("types", EmployeeGender.values());
			model.addAttribute("employees", employeeServiceImp.findAll());
			model.addAttribute("salespersons", salespersonRepo.findAll());
		}

		return "employee/update-employee";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String updateEmployee(@PathVariable("id") Integer id, @RequestParam(value = "action") String action, @ModelAttribute("employee") @Validated Employee employee, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("employee", employee);
			model.addAttribute("types", EmployeeGender.values());
			model.addAttribute("salespersons", salespersonRepo.findAll());
			
			return "empployee/update-employee";
		}
		
		if(action != null && action.equals("Save changes")) {
			employeeServiceImp.editEmployee(id, employee);
			model.addAttribute("employees", employeeServiceImp.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/employee/";
		}
		
		return "redirect:/employee/";
	}
}
