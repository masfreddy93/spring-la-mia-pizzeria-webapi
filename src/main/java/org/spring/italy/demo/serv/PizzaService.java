package org.spring.italy.demo.serv;

import java.util.List;
import java.util.Optional;

import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaRepo pizzaRepo;
	
	
	public Pizza save(Pizza pizza) {
		
		return pizzaRepo.save(pizza);
	}
	public List<Pizza> findAll(){
		
		return pizzaRepo.findAll();
	}
	public Optional<Pizza> findPizzaById(int id){
		
		return pizzaRepo.findById(id);
	}
	public void delete(Pizza pizza) {
		
		pizzaRepo.delete(pizza);
	}
	public void deleteById(int id) {
		
		pizzaRepo.deleteById(id);
	}
	public List<Pizza> findByName(String name){
		
		return pizzaRepo.findByNameContainingIgnoreCase(name);
	}
}
