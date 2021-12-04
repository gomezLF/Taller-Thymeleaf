package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.hr.Employee;

public interface EmployeeController {
	public String addEmployee(Model model);
	public String indexEmployee(Model model);
	public String saveEmployee(@RequestParam(value = "action", required = true) String action, @ModelAttribute("employee") @Validated Employee employee, BindingResult result, Model model);
	public String updateEmployee(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("employee") @Validated Employee employee, BindingResult bindingResult, Model model);
	public String showUpdateEmployee(@PathVariable("id") Integer id, Model model);
}
