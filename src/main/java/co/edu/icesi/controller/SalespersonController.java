package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.sales.Salesperson;

public interface SalespersonController {
	
	public String addSalesperson(Model model);
	public String indexSalesperson(Model model);
	public String saveSalesperson(@ModelAttribute("salesperson") @Validated Salesperson salesperson, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String updateSalesperson(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesperson") @Validated Salesperson salesperson, BindingResult bindingResult, Model model);
	public String showUpdateSalesperson(@PathVariable("id") Integer id, Model model);
}
