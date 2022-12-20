package org.spring.italy.demo.api.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Ingrediente;
import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/ingredients")
@CrossOrigin("*")
public class IngredienteApiController {

	@Autowired PizzaService pizzaServ;
	
	@GetMapping("/by/pizza/{id}")
	public List<Ingrediente> getIngredients(@PathVariable("id") int id){
		
		Pizza pizza = pizzaServ.findPizzaById(id).get();
		
		return pizza.getIngredients();
	}
}
