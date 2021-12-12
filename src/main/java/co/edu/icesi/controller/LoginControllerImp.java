package co.edu.icesi.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.edu.icesi.model.person.User;

@Controller
public class LoginControllerImp implements LoginController {

	@Override
	@GetMapping("/login")
	public String login(@NotNull Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@Override
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@Override
	@GetMapping("/access-denied")
	public String accessDeniedPage(@Param(value = "url") String url, Model model) {
		if(url == null) {
			url = "/";
		}
		model.addAttribute("url", url);
		return "denied";
	}

}
