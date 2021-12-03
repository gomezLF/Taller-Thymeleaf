package co.edu.icesi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	 @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {

	        httpSecurity.cors().and().authorizeRequests()
	                .antMatchers("/salesPerson/").access("hasRole('administrator')")
	                .antMatchers("/creditCard/").access("hasRole('operator')")
	                .antMatchers("/salesOrdersHeader/").access("hasRole('operator')")
	                .antMatchers("/salesOrderDetail/").access("hasRole('operator')")
	                .anyRequest().authenticated().and()
	                .formLogin().loginPage("/login").permitAll().and()
	                .logout().invalidateHttpSession(true).clearAuthentication(true)
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
	                .permitAll().and()
	                .exceptionHandling().accessDeniedHandler((AccessDeniedHandler) accessDeniedHandler);
	    }

}
