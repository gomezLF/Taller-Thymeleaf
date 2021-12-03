package co.edu.icesi.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;

public interface LoginController {
	public String login(Model model);
	public String index();
	public String accessDeniedPage(@Param(value = "url") String url, Model model);
}
