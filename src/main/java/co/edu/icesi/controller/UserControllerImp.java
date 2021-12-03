package co.edu.icesi.controller;

import java.util.Optional;

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

import co.edu.icesi.model.person.User;
import co.edu.icesi.model.person.UserType;
import co.edu.icesi.services.UserServiceImp;

@Controller
@RequestMapping("/user")
public class UserControllerImp implements UserController {
	
	private UserServiceImp userServiceImp;
	
	public UserControllerImp(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}
	
	@Override
	@GetMapping("/add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
        model.addAttribute("types", UserType.values());
        return "user/add-user";
	}

	@Override
	@PostMapping("/add")
	public String saveUser(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if(result.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("user", user);
        	model.addAttribute("types", UserType.values());
			return "/user/add-user";
		}
		
		if(action != null && action.equals("Add")) {
			String pwd = user.getUserPassword();
			
			if(!pwd.startsWith("{noop}") || pwd.equals("{noop}")) { 
            	user.setUserPassword("{noop}" + pwd);
            }
            userServiceImp.save(user);
			model.addAttribute("users", userServiceImp.findAll());
			
			
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/user/";
		}
        return "redirect:/user/";
	}
	
	@Override
	@GetMapping
	public String indexUser(Model model) {
		model.addAttribute("users", userServiceImp.findAll());
		return "user/index";
	}

	@Override
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<User> user = userServiceImp.findById(id);
		
		if(user == null) {
			throw new IllegalArgumentException("Invalid user Id:" + id);
		}
		model.addAttribute("user", user.get());
        model.addAttribute("types", UserType.values());
        model.addAttribute("users", userServiceImp.findAll());
        return "user/update-user";
	}

	@Override
	@PostMapping("/edit/{id}")
	public String updateUser(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, @ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() && (action != null && !action.equals("Cancel"))) {
			model.addAttribute("types", UserType.values());
			return "user/update-user";
		}
		
		if(action != null && action.equals("Save Changes")) {
            userServiceImp.updateUser(user);
            model.addAttribute("users", userServiceImp.findAll());
            
		}else if(action != null && action.equals("Cancel")) {
			return "redirect:/user/";
		}
        return "redirect:/user/";
	}

}
