package com.sylviadang.loginandreg.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.sylviadang.loginandreg.models.LoginUser;
import com.sylviadang.loginandreg.models.User;
import com.sylviadang.loginandreg.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User registerUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return userRepository.save(user);
	}
	public User getUser(String email) {
		Optional<User> potentialUser = userRepository.findByEmail(email);
		return potentialUser.isPresent() ? potentialUser.get() : null;
	}
	
	public User getUser(Long id) {
		Optional<User> potentialUser = userRepository.findById(id);
		return potentialUser.isPresent() ? potentialUser.get() : null;
	}
	
	public User login(LoginUser loginUser, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}
		User existingUser = this.getUser(loginUser.getEmail());
		if(existingUser == null) {
			result.rejectValue("email","Match", "invalid Cred!");
			return null;
		}
		if (!BCrypt.checkpw(loginUser.getPassword(), existingUser.getPassword())) {
			result.rejectValue("email", "Match", "invalid cred!");
			return null;
		}
		return existingUser;
	}
}
 