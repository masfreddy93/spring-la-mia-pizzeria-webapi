package org.spring.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.spring.italy.demo.pojo.Drink;
import org.spring.italy.demo.pojo.Ingrediente;
import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.pojo.Promozione;
import org.spring.italy.demo.serv.DrinkService;
import org.spring.italy.demo.serv.IngredienteService;
import org.spring.italy.demo.serv.PizzaService;
import org.spring.italy.demo.serv.PromozioneServ;
import org.spring.italy.demo.serv.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private DrinkService drinkService;
	@Autowired
	private PromozioneServ promoService;
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping
	public String index(Model model) {
		
		List<Pizza> pizze = pizzaService.findAll();
		model.addAttribute("pizze", pizze);
		
		return "pizza/index";
	}
	
	
	@GetMapping("pizza/{id}")
	public String getPizzaById(@PathVariable("id") int id, Model model) {
		
		Optional<Pizza> optPizza = pizzaService.findPizzaById(id);
		
		if(optPizza.isEmpty()) {
			
			System.out.println("Pizza con id: " + id + " non presente");
		}
		
		Pizza pizza = optPizza.get();
		model.addAttribute("pizza", pizza);
		
		return "pizza/pizza";
	}
	
	
	@GetMapping("pizze/create")
	public String createPizza(Model model) {
		
		Pizza pizza = new Pizza();
		model.addAttribute("pizza", pizza);
		List<Promozione> promos = promoService.findAllWPizza();
		model.addAttribute("promos", promos);
		List<Ingrediente> ingredients = ingredienteService.findAll();
		model.addAttribute("ingredients", ingredients);
		
		return "pizza/pizza-create";
	}
	@PostMapping("pizze/create")
	public String storePizza(@Valid Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			
			System.err.println("ERRORE");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			
			return "redirect:/pizze/create";
		}
		
		try {
			
			pizzaService.save(pizza);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/pizze/create";
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("pizze/update/{id}")
	public String editPizza(@PathVariable("id") int id, Model model) {
		
		Optional<Pizza> optPizza = pizzaService.findPizzaById(id);
		Pizza pizza = optPizza.get();
		List<Promozione> promos = promoService.findAll();
		model.addAttribute("promos", promos);
		List<Ingrediente> ingredients = ingredienteService.findAll();
		model.addAttribute("ingredients", ingredients);
		
		model.addAttribute("pizza", pizza);
		
		return "pizza/pizza-update";
	}
	@PostMapping("pizze/store")
	public String updatePizza(@Valid Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			
			System.err.println("ERRORE");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			
			return "redirect:/pizze/update/" + pizza.getId();
		}
		
		try {
			
			pizzaService.save(pizza);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/pizze/update/" + pizza.getId();
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("pizze/delete/{id}")
	public String deletePizza(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
		try {
			
			Optional<Pizza> optPizza = pizzaService.findPizzaById(id);
			Pizza pizza = optPizza.get();
			
			pizzaService.delete(pizza);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("pizza/search")
	public String getSearchPizzaByName(Model model, @RequestParam(name = "q", required = false) String query) {
		
		List<Pizza> pizze = null;
		if (query == null) {
			
			pizze = pizzaService.findAll();
			
		} else {
			
			pizze = pizzaService.findByName(query);
		}
		
		
		model.addAttribute("pizze", pizze);
		model.addAttribute("query", query);
		
		
		return "pizza/pizza-search";
	}
	
	
	
	
	@GetMapping("/search")
	public String getSearchDrinkAndPizzaByName(Model model, @RequestParam(name = "q", required = false) String query) {
		
		List<Pizza> pizze = null;
		List<Drink> drinks = null;
		
		if (query == null) {
			
			pizze = pizzaService.findAll();
			drinks = drinkService.findAll();
			
		} else {
			
			pizze = pizzaService.findByName(query);
			drinks = drinkService.findByName(query);
		}
		
		
		model.addAttribute("pizze", pizze);
		model.addAttribute("drinks", drinks);
		model.addAttribute("query", query);
		
		return "drink-pizza-search";
	}
	
	
}
