package org.spring.italy.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.spring.italy.demo.pojo.Role;
import org.spring.italy.demo.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DatabaseUserDetails implements UserDetails{

	
	private static final long serialVersionUID = 8317498361092727083L;
	private final User user;
	
	public DatabaseUserDetails(User user) {
		
		this.user = user;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<Role> roles = user.getRoles();
		Set <GrantedAuthority> grantRoles = new HashSet<>();
		
		for(Role role : roles)
			grantRoles.add(new SimpleGrantedAuthority(role.getName()));
		
		return grantRoles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
}
