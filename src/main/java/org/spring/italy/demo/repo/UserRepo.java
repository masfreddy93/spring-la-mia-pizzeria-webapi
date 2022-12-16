package org.spring.italy.demo.repo;


import java.util.Optional;

import org.spring.italy.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
}
