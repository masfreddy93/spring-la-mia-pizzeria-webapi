package org.spring.italy.demo.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.pojo.Promozione;
import org.spring.italy.demo.serv.PizzaService;
import org.spring.italy.demo.serv.PromozioneServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/promo")
public class PromoController {

	@Autowired
	private PromozioneServ promoServ;
	@Autowired
	private PizzaService pizzaServ;
	
	@GetMapping
	public String index(Model model) {
		
		List<Promozione> promos = promoServ.findAll();
		model.addAttribute("promos", promos);
		
		
		return "promo/index";
	}
	
	
	@GetMapping("/create")
	public String createPromo(Model model) {
		
		Promozione promo = new Promozione();		
		model.addAttribute("promo", promo);
		List<Pizza> pizze = pizzaServ.findAll();
		model.addAttribute("pizze", pizze);
		
		return "promo/promo-create";
	}
	
	
	@PostMapping("/create")
	public String storePromo(@Valid Promozione promo, BindingResult bindingResult, 
				RedirectAttributes redirectAttributes) {
	
		List<Pizza> listOfPizzas = promo.getListOfPizzas();
		
		for(Pizza pizza : listOfPizzas) {
			
			pizza.setPromo(promo);
		}
		
		if(bindingResult.hasErrors()) {
			
			System.err.println("ERRORE");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			
			return "redirect:/promo/create";
		}
		
		try {
			
			promoServ.save(promo);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/promo/create";
		}
		
				
		
		return "redirect:/promo";
	}
}
