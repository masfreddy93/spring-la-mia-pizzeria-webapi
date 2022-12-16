package org.spring.italy.demo.api.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
