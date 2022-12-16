package org.spring.italy.demo.serv;

import java.util.List;
import java.util.Optional;
import org.spring.italy.demo.pojo.Drink;
import org.spring.italy.demo.repo.DrinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {

	@Autowired
	private DrinkRepo drinkRepo;
	
	
	public void save(Drink d1) {
		
		drinkRepo.save(d1);
	}
	public List<Drink> findAll(){
		
		return drinkRepo.findAll();
	}
	public Optional<Drink> findDrinkById(int id) {
		
		return drinkRepo.findById(id);
	}
	public void delete(Drink drink){
		
		drinkRepo.delete(drink);
	}
	public List<Drink> findByName(String name){
		
		return drinkRepo.findByNameContainingIgnoreCase(name);
	}
	public void savePriceable(Drink d1) {
		
		drinkRepo.save(d1);
	}
}
