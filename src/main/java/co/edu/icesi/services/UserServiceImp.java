package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.model.person.User;
import co.edu.icesi.repositories.UserRepo;

@Service
public class UserServiceImp implements UserService {
	
	private UserRepo userRepository;

    @Autowired
    public UserServiceImp(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	public Iterable<User> saveAll(Iterable<User> userclasses) {
		for(User userclass : userclasses) {
			save(userclass);
		}
		return userclasses;
	}

	@Override
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public void updateUser(User user) {
		Optional<User> temp = findById(user.getUserId());
		
		if(temp.isPresent()) {
			temp.get().setUserName(user.getUserName());
			temp.get().setUserType(user.getUserType());
			
			if(user.getUserPassword().startsWith("{noop}")) {
				temp.get().setUserPassword(user.getUserPassword());
			}else {
				temp.get().setUserPassword("{noop}" + user.getUserPassword());
			}
			
			userRepository.save(temp.get());
		}
	}

}