package org.spring.italy.demo.api.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/1/pizze")
@CrossOrigin("*")
public class PizzaApiController {

	@Autowired PizzaService pizzaServ;
	
	@GetMapping("/all")
	public List<Pizza> getPizzas(){
		
		List<Pizza> pizzas = pizzaServ.findAll();
		return pizzas;
	}
	
	
	@PostMapping("/create")
	public Pizza createPizza(@Valid @RequestBody Pizza pizza) {
		
		System.err.println(pizza);
		
		Pizza newPizza = pizzaServ.save(pizza);
		
		System.err.println(newPizza);
		
		return newPizza;
	}
	
	@PostMapping("/update/{id}")
	public Pizza updatePizza(@PathVariable("id") int id, @Valid @RequestBody Pizza pizza) {
		
		Pizza newPizza = pizzaServ.save(pizza);
		
		return newPizza;

	}
	
	
	@GetMapping("/delete/{id}")
	public boolean deletePizzaById(@PathVariable("id") int id) {
		
		try {
			
			Pizza p = pizzaServ.findPizzaById(id).get();
			pizzaServ.delete(p);			
		} catch(Exception e){
			
			return false;
		}
		
		
		return true;
	}
	
	
	
}
