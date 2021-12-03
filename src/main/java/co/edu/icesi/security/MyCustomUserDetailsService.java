package co.edu.icesi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import co.edu.icesi.repositories.UserRepo;


@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	private UserRepo userRepository;
	
	@Autowired
	public MyCustomUserDetailsService(UserRepo userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		co.edu.icesi.model.person.User user = userRepository.findByUserName(username);
		if (user != null) {
			User.UserBuilder builder = User.withUsername(username).password(user.getUserPassword()).roles(user.getUserType().toString());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}