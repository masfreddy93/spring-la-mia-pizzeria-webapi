package org.spring.italy.demo.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Ingrediente;
import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.serv.IngredienteService;
import org.spring.italy.demo.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredienteController {

	@Autowired IngredienteService ingredienteService;
	@Autowired PizzaService pizzaService;
	
	@GetMapping
	public String index(Model model) {
		
		List<Ingrediente> listOfIngredients = ingredienteService.findAll();
		model.addAttribute("ingredients", listOfIngredients);
		
		return "ingrediente/index";
	}
	
	@GetMapping("/create")
	public String createIngrediente(Model model) {
		
		Ingrediente ingrediente = new Ingrediente();
		model.addAttribute("ingrediente", ingrediente);
		List<Pizza> pizze = pizzaService.findAll();
		model.addAttribute("pizzas", pizze);
		
		return "ingrediente/ingrediente-create";
	}
	
	@PostMapping("/create")
	public String storeIngrediente(@Valid Ingrediente ingrediente,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		List<Pizza> pizze = ingrediente.getPizzas();
		for(Pizza pizza : pizze) {
			
			pizza.getIngredients().add(ingrediente);
		}
		
		
		if(bindingResult.hasErrors()) {
			
			System.err.println("ERRORE");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			
			return "redirect:/ingredients/create";
		}
		
		try {
			
			ingredienteService.save(ingrediente);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/ingredients/create";
		}
		
		return "redirect:/ingredients";
	}
	
	
	@GetMapping("/update/{id}")
	public String editIngrediente(@PathVariable("id") int id, Model model) {
		
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		List<Pizza> pizze = pizzaService.findAll();
		model.addAttribute("pizzas", pizze);
		
		return "ingrediente/ingrediente-update";
	}
	@PostMapping("/store/{id}")
	public String updateIngrediente(@PathVariable("id") int id, 
			@Valid Ingrediente ingrediente, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		
		//recupero dati vecchi (quelli dal DB) tramite id dell'ingrediente
		Ingrediente oldIngrediente = ingredienteService.findById(id);
		
		//elimino dati vecchi (quelli del DB)
		for(Pizza pizza : oldIngrediente.getPizzas()) {
			
			pizza.getIngredients().remove(oldIngrediente);
		}
		
		//salvo dati nuovi (inseriti tramite form)
		for(Pizza pizza : ingrediente.getPizzas()) {
			
			pizza.addIngredients(ingrediente);
		}
		
		
		if(bindingResult.hasErrors()) {
			
			System.err.println("ERRORE");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			
			return "redirect:/ingredients/update/" + ingrediente.getId();
		}
		
		try {
			
			//salvo ingrediente
			ingredienteService.save(ingrediente);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/ingredients/update/" + ingrediente.getId();
		}
		
		
		return "redirect:/ingredients";
	}
	
	
	
	@GetMapping("/delete/{id}")
	public String deleteIngrediente(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
		try {
			
			Ingrediente ingrediente = ingredienteService.findById(id);
			ingredienteService.delete(ingrediente);
			
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
		}
		
		
		return "redirect:/ingredients";
	}
	
	
}
