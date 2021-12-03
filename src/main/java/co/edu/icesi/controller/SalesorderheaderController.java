package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.sales.Salesorderheader;

public interface SalesorderheaderController {
	
	public String addSalesorderheader(Model model);
	public String indexSalesorderheader(Model model);
	public String saveSalesorderheader(@ModelAttribute("soh") @Validated Salesorderheader soh, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String updateSalesorderheader(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesorderheader") @Validated Salesorderheader salesorderheader, BindingResult bindingResult, Model model);
	public String showUpdateSalesorderheader(@PathVariable("id") Integer id, Model model);
}
