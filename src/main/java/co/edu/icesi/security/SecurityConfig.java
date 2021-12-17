package co.edu.icesi.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import co.edu.icesi.model.person.UserType;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Override
    protected void configure(@NotNull HttpSecurity httpSecurity) throws Exception {
	 	
	 	httpSecurity.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
 		.antMatchers("/salesperson/**").hasRole(UserType.ADMINISTRATIOR.toString())
 		.antMatchers("/creditcard/**", "/salesorderheader/**", "/salesorderdetail/**").hasRole(UserType.OPERATOR.toString())
 		.anyRequest().authenticated().and()
		.httpBasic().and().logout().invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
	}
}
