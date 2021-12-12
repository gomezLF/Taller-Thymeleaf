package co.edu.icesi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.EmployeeRepo;
import co.edu.icesi.services.SalespersonServiceImp;

@Controller
@RequestMapping("/salesperson")
public class SalespersonControllerImp implements SalespersonController {
	
	private SalespersonServiceImp salespersonService;
	private EmployeeRepo employeeRepo;
	
	
	@Autowired
	public SalespersonControllerImp(SalespersonServiceImp salespersonService, EmployeeRepo employeeRepo) {
		this.salespersonService = salespersonService;
		this.employeeRepo = employeeRepo;
	}
	
	
	@Override
	@GetMapping("/add")
	public String addSalesperson(Model model) {
		model.addAttribute("employeess", employeeRepo.findAll());
		model.addAttribute("salesperson", new Salesperson());
		return "/salesperson/add-salesperson";
	}
	
	@Override
	@PostMapping("/add")
	public String saveSalesperson(@ModelAttribute("salesperson") @Validated Salesperson salesperson, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("employeess", employeeRepo.findAll());
			model.addAttribute("salesperson", salesperson);
			return "/salesperson/add-salesperson";
		}
		
		if(action != null && action.equals("Add")) {
			salespersonService.saveSalesPerson(salesperson);
			model.addAttribute("salespersons", salespersonService.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/salesperson/";
		}
		
		return "redirect:/salesperson/";
	}
	
	@Override
	@GetMapping
	public String indexSalesperson(Model model) {
		model.addAttribute("salespersons", salespersonService.findAll());
		return "salesperson/index";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateSalesperson(@PathVariable("id") Integer id, Model model) {
		Optional<Salesperson> salesperson = salespersonService.findSalesperson(id);
		
		if(salesperson.isPresent()) {
			new IllegalArgumentException("Invalid soh Id:" + id);
		}

		model.addAttribute("employeess", employeeRepo.findAll());
		model.addAttribute("salesperson", salesperson.get());
		model.addAttribute("salespersons", salespersonService.findAll());
		return "salesperson/update-salesperson";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String updateSalesperson(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesperson") @Validated Salesperson salesperson, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("employeess", employeeRepo.findAll());
			model.addAttribute("salesperson", salesperson);
			return "salesperson/update-salesperson";
		}
		
		if(action != null && action.equals("Save changes")) {
			salespersonService.editSalesPerson(salesperson);
			model.addAttribute("salespersons", salespersonService.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/salesperson/";
		}
		
		return "redirect:/salesperson/";
	}
}
