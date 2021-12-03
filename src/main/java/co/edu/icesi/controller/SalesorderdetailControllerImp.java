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
import co.edu.icesi.model.person.UserType;
import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.model.sales.SalesorderdetailPK;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.services.SalesorderdetailServiceImp;

@Controller
@RequestMapping("/salesorderdetail")
public class SalesorderdetailControllerImp implements SalesorderdetailController {
	
	private SalesorderdetailServiceImp salesorderdetailService;
	private SalesorderheaderRepo salesorderheaderRepo;
	
	@Autowired
	public SalesorderdetailControllerImp(SalesorderdetailServiceImp salesorderdetailService, SalesorderheaderRepo salesorderheaderRepo) {
		this.salesorderdetailService = salesorderdetailService;
		this.salesorderheaderRepo = salesorderheaderRepo;
	}
	
	
	@Override
	@GetMapping("/add")
	public String addSalesorderdetail(Model model) {
		model.addAttribute("salesorderdetail", new Salesorderdetail());
		return "salesorderdetail/add-salesorderdetail";
	}
	
	@Override
	@PostMapping("/add")
	public String saveSalesorderdetail(@ModelAttribute("salesorderdetail") @Validated Salesorderdetail salesorderdetail, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("salesorderdetail", salesorderdetail);
			return "/salesorderdetail/add-salesorderdetail";
		}
		
		if(action != null && action.equals("Add")) {
			salesorderdetailService.saveSalesOrderDetails(salesorderdetail);
			model.addAttribute("salesorderdetails", salesorderdetailService.findAll());
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/salesorderdetail/";
		}
		
		return "redirect:/salesorderdetail/";
	}
	
	@Override
	@GetMapping
	public String indexSalesorderdetail(Model model) {
		model.addAttribute("salesorderdetails", salesorderdetailService.findAll());
		return "salesorderdetail/index";
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateSalesorderdetail(@PathVariable("id") SalesorderdetailPK id, Model model) {
		Optional<Salesorderdetail> salesOptional = salesorderdetailService.findSalesorderdetail(id.getSalesorderdetailid());
		
		if(salesOptional == null) {
			throw new IllegalArgumentException("Invalid user Id:" + id.getSalesorderdetailid());
		}
		
		model.addAttribute("salesorderdetail", salesOptional.get());
		model.addAttribute("salesorderdetails", salesorderdetailService.findAll());
		return "salesorderdetail/update-salesorderdetail";
	}
	
	@Override
	@PostMapping("/edit/{id}")
	public String updateSalesorderdetail(@PathVariable("id") SalesorderdetailPK id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesorderdetail") @Validated Salesorderdetail salesorderdetail, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			return "user/update-user";
		}
		
		if(action != null && action.equals("Save Changes")) {
            salesorderdetailService.editSalesOrderDetails(salesorderdetail);
            model.addAttribute("salesorderdetails", salesorderdetailService.findAll());
            
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/user/";
		}
		
		return "redirect:/salesorderdetail/";
	}
}
