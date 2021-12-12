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
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.repositories.SalespersonRepo;
import co.edu.icesi.services.SalesorderheaderServiceImp;

@Controller
@RequestMapping("/salesorderheader")
public class SalesorderheaderControllerImp implements SalesorderheaderController {
	
	private SalesorderheaderServiceImp salesorderheaderService;
	private SalespersonRepo salespersonRepo;
	private CreditcardRepo creditcardRepo;
	
	@Autowired
	public SalesorderheaderControllerImp(SalesorderheaderServiceImp salesorderheaderService, SalespersonRepo salespersonRepo, CreditcardRepo creditcardRepo) {
		this.salesorderheaderService = salesorderheaderService;
		this.salespersonRepo = salespersonRepo;
		this.creditcardRepo = creditcardRepo;
	}
	
	
	@Override
	@GetMapping("/add")
	public String addSalesorderheader(Model model) {
		model.addAttribute("salespersons", salespersonRepo.findAll());
		model.addAttribute("creditcards", creditcardRepo.findAll());
		model.addAttribute("salesorderheader", new Salesorderheader());
		return "salesorderheader/add-salesorderheader";
	}
	
	@Override
	@PostMapping("/add")
	public String saveSalesorderheader(@ModelAttribute("salesorderheader") @Validated Salesorderheader salesorderheader, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("salespersons", salespersonRepo.findAll());
			model.addAttribute("creditcards", creditcardRepo.findAll());
			model.addAttribute("salesorderheader", salesorderheader);
			return "/salesorderheader/add-salesorderheader";
		}
		
		if(action != null && action.equals("Add")) {
			salesorderheaderService.saveSalesOrderHeader(salesorderheader);
			model.addAttribute("salesorderheaders", salesorderheaderService.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/salesorderheader/";
		}
		
		return "redirect:/salesorderheader/";
	}
	
	@Override
	@GetMapping
	public String indexSalesorderheader(Model model) {
		model.addAttribute("salesorderheaders", salesorderheaderService.findAll());
		return "salesorderheader/index";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateSalesorderheader(@PathVariable("id") Integer id, Model model) {
		Optional<Salesorderheader> salesorderheader = salesorderheaderService.findSalesorderheader(id);
		
		if(!salesorderheader.isPresent()) {
			new IllegalArgumentException("Invalid soh Id:" + id);
		}

		model.addAttribute("salespersons", salespersonRepo.findAll());
		model.addAttribute("creditcards", creditcardRepo.findAll());
		model.addAttribute("salesorderheader", salesorderheader.get());
		model.addAttribute("salesorderheaders", salesorderheaderService.findAll());
		return "salesorderheader/update-salesorderheader";
	}

	@Override
	@PostMapping("/edit/{id}")
	public String updateSalesorderheader(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesorderheader") @Validated Salesorderheader salesorderheader, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("salespersons", salespersonRepo.findAll());
			model.addAttribute("creditcards", creditcardRepo.findAll());
			model.addAttribute("salesorderheader", salesorderheader);
			return "salesorderheader/update-salesorderheader";
		}
		
		if(action != null && action.equals("Save changes")) {
			salesorderheaderService.editSalesOrderHeader(salesorderheader);
			model.addAttribute("salesorderheaders", salesorderheaderService.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/salesorderheader/";
		}
		
		return "redirect:/salesorderheader/";
	}
}
