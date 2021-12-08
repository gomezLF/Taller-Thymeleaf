package co.edu.icesi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import co.edu.icesi.model.person.UserType;



public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
	 	
	 	httpSecurity.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
 		.antMatchers("/salesperson/**").hasRole(UserType.ADMINISTRATIOR.toString())
 		.antMatchers("/creditcard/**", "/creditcard/**", "/salesordersheader/**", "/salesorderdetail/**").hasRole(UserType.OPERATOR.toString())
 		.anyRequest().authenticated().and()
		.httpBasic().and().logout().invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
	}
}
