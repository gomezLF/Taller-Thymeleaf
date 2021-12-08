package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.sales.Salesorderdetail;

public interface SalesorderdetailController {
	
	public String addSalesorderdetail(Model model);
	public String indexSalesorderdetail(Model model);
	public String saveSalesorderdetail(@ModelAttribute("sod") @Validated Salesorderdetail sod, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String updateSalesorderdetail(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("salesorderdetail") @Validated Salesorderdetail salesorderdetail, BindingResult bindingResult, Model model);
	public String showUpdateSalesorderdetail(@PathVariable("id") Integer id, Model model);
}
