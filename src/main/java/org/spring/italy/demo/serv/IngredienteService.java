package org.spring.italy.demo.serv;

import java.util.List;

import org.spring.italy.demo.pojo.Ingrediente;
import org.spring.italy.demo.repo.IngredienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

	@Autowired IngredienteRepo ingredienteRepo;
	
	public void save(Ingrediente ingrediente) {
		
		ingredienteRepo.save(ingrediente);
	}
	
	public void delete(Ingrediente ingrediente) {
		
		ingredienteRepo.delete(ingrediente);
	}
	
	public List<Ingrediente> findAll(){
		
		return ingredienteRepo.findAll();
	}
	
	public Ingrediente findById(int id) {
		
		return ingredienteRepo.findById(id).get();
	}
	
}
