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

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.services.CreditcardServiceImp;

@Controller
@RequestMapping("/creditcard")
public class CreditcardControllerImp implements CreditcardController {

	private CreditcardServiceImp creditcardServiceImp;
	
	@Autowired
	public CreditcardControllerImp(CreditcardServiceImp creditcardServiceImp) {
		this.creditcardServiceImp = creditcardServiceImp;
	}
	
	@Override
	@GetMapping("/add")
	public String addCreditcard(Model model) {
		model.addAttribute("creditcard", new Creditcard());
		model.addAttribute("types", CreditcardType.values());
		return "/creditcard/add-creditcard";
	}
	
	@Override
	@PostMapping("/add")
	public String saveCreditcard(@RequestParam(value = "action", required = true) String action, @ModelAttribute("creditcard") @Validated Creditcard creditcard, BindingResult result, Model model) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("creditcard", creditcard);
			model.addAttribute("types", CreditcardType.values());
			return "/creditcard/add-creditcard";
		}
		
		if(action != null && action.equals("Add")) {
			creditcardServiceImp.saveCreditCard(creditcard);
			model.addAttribute("creditcards", creditcardServiceImp.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/creditcard/";
		}
		
		return "redirect:/creditcard/";
	}

	@Override
	@GetMapping
	public String indexCreditcard(Model model) {
		model.addAttribute("creditcards", creditcardServiceImp.findAll());
		return "creditcard/index";
	}

	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateCreditcard(@PathVariable("id") Integer id, Model model) {
		Optional<Creditcard> creditcard = creditcardServiceImp.findCreditCard(id);
		
		if(!creditcard.isPresent()) {
			new IllegalArgumentException("Invalid soh Id:" + id);
		}
		
		model.addAttribute("creditcard", creditcard.get());
		model.addAttribute("types", CreditcardType.values());
		model.addAttribute("creditcards", creditcardServiceImp.findAll());
		return "creditcard/update-creditcard";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String updateCreditcard(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("creditcard") @Validated Creditcard creditcard, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("types", CreditcardType.values());
			return "creditcard/update-creditcard";
		}
		
		if(action != null && action.equals("Save changes")) {
			try {
				creditcardServiceImp.editCreditCard(creditcard);
			} catch (LogicalException e) {
				e.printStackTrace();
			}
			
			model.addAttribute("creditcards", creditcardServiceImp.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/creditcard/";
		}
		
		return "redirect:/creditcard/";
	}

}
