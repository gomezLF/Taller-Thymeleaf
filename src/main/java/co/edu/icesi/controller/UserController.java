package co.edu.icesi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.model.person.User;

public interface UserController {
	public String addUser(Model model);
	public String indexUser(Model model);
	public String saveUser(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateUser(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model);
}
