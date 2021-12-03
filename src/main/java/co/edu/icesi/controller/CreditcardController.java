package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.sales.Creditcard;

public interface CreditcardController {
	
	public String addCreditcard(Model model);
	public String indexCreditcard(Model model);
	public String saveCreditcard(@RequestParam(value = "action", required = true) String action, @ModelAttribute("creditcard") @Validated Creditcard creditcard, BindingResult result, Model model);
	public String updateCreditcard(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("creditcard") @Validated Creditcard creditcard, BindingResult bindingResult, Model model);
	public String showUpdateCreditcard(@PathVariable("id") Integer id, Model model);
}
