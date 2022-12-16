package org.spring.italy.demo.serv;

import java.util.List;
import java.util.Optional;

import org.spring.italy.demo.pojo.User;
import org.spring.italy.demo.repo.UserRepo;
import org.spring.italy.demo.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServ implements UserDetailsService{

	@Autowired UserRepo userRepo;
	
	public void save(User user) {
		
		userRepo.save(user);
	}
	public void delete(User user) {
		
		userRepo.delete(user);
	}
	public List<User> findAll(){
		
		return userRepo.findAll();
	}
	public User findById(int id) {
		
		return userRepo.findById(id).get();
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		
		Optional<User> userOpt = userRepo.findByUsername(username);
		
		if(userOpt.isEmpty())
			throw new UsernameNotFoundException("User not found");
		
		return new DatabaseUserDetails(userOpt.get());
	}
}
