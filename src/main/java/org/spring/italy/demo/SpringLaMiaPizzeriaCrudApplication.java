package org.spring.italy.demo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.spring.italy.demo.pojo.Drink;
import org.spring.italy.demo.pojo.Ingrediente;
import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.pojo.Promozione;
import org.spring.italy.demo.serv.DrinkService;
import org.spring.italy.demo.serv.IngredienteService;
import org.spring.italy.demo.serv.PizzaService;
import org.spring.italy.demo.serv.PromozioneServ;
import org.spring.italy.demo.serv.RoleServ;
import org.spring.italy.demo.serv.UserServ;
import org.spring.italy.demo.pojo.Role;
import org.spring.italy.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private DrinkService drinkService;
	@Autowired
	private PromozioneServ promoService;
	@Autowired
	private IngredienteService ingredienteService;
	@Autowired private UserServ userServ;
	@Autowired private RoleServ roleServ;
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		//PROMOZIONI
		//inserimento
		Promozione pr1 = new Promozione(LocalDate.of(2022, 12, 01), LocalDate.of(2022, 12, 25), "Sconto pazzo 60%");
		Promozione pr2 = new Promozione(LocalDate.of(2022, 01, 01), LocalDate.of(2022, 12, 31), "Sconto 2022");
		Promozione pr3 = new Promozione(LocalDate.of(2022, 05, 29), LocalDate.of(2022, 10, 25), "Sconto 20%");
		
		promoService.save(pr1);
		promoService.save(pr2);
		promoService.save(pr3);
		
		//lettura
		List<Promozione> promotions = promoService.findAll();
		System.out.println(promotions);
		
		
		//PIZZE
		//inserimento
		Pizza p1 = new Pizza(pr1, "Margherita", "La classica delle pizze", 4);
		Pizza p2 = new Pizza(pr1, "Capricciosa", "Deliziosa come sempre", 7);
		Pizza p3 = new Pizza(pr2, "Tonno e cipolla", "Attento all'alito", 6);
		Pizza p4 = new Pizza(null, "Salamino", "Piccantissima", 6);
		
		pizzaService.save(p1);
		pizzaService.save(p2);
		pizzaService.save(p3);
		pizzaService.save(p4);
		
		
		//lettura
		List<Pizza> pizze = pizzaService.findAll();
		System.out.println(pizze);

		
		//DRINK
		//inserimento
		Drink d1 = new Drink("Coca cola", "Stay classic", 3);
		Drink d2 = new Drink("Fanta", "Ci sta sempre", 3);
		Drink d3 = new Drink("Acqua", "Mai banale", 1);
		
		drinkService.save(d1);
		drinkService.save(d2);
		drinkService.save(d3);
		
		
		//lettura
		List<Drink> drinks = drinkService.findAll();
		System.out.println(drinks);
		
		
		//test relazione pizza-promo
		Pizza pizzaToDelete = pizzaService.findPizzaById(2).get();
		pizzaService.delete(pizzaToDelete);
		
		List<Pizza> pizzas = pizzaService.findAll();
		for(Pizza pizza : pizzas) {
			
			System.err.println(pizza + "\n\t" + pizza.getPromo());
		}
		
		Promozione promoToDelete = promoService.findById(1);
		promoService.delete(promoToDelete);
		
		List<Promozione> promos = promoService.findAllWPizza();
		for(Promozione promo : promos) {
			
			System.err.println(promo);
			
			for(Pizza pizza : promo.getListOfPizzas()) {
				
				System.err.println("\t" + pizza);
			}
		}
		
		
		//INGREDIENTE
		//inserimento
		Ingrediente i1 = new Ingrediente("tonno");
		Ingrediente i2 = new Ingrediente("cipolla");
		Ingrediente i3 = new Ingrediente("mozzarella");
		Ingrediente i4 = new Ingrediente("olive");
		
		ingredienteService.save(i1);
		ingredienteService.save(i2);
		ingredienteService.save(i3);
		ingredienteService.save(i4);
		
		//lettura
		List<Ingrediente> listOfIngredients = ingredienteService.findAll();
		System.out.println(listOfIngredients);
		
		
		//TEST RELAZIONE M2M (many-to-many)
		//prima dalla pizza (è la parte "forte", si crea senza problemi la relazione
		List<Ingrediente> ingredientsForTonnOlive = Arrays.asList(new Ingrediente[] {
			i1,
			i2,
			i4
		});
		
		Pizza p5 = new Pizza(pr3, "TonnOlive", "Lasciati avvolgere dalle olive della cornovalia", 8, ingredientsForTonnOlive);
		pizzaService.save(p5);
		System.out.println(p5);
		System.out.println(p5.getIngredients());   //ok
		
		
		//ora partiamo da ingredienti (è la parte debole della relaz xk ha il mappedBy
		
		//questa relazione non verrà valorizzata nel DB
		Ingrediente i5 = new Ingrediente("pomodoro", pizzas);
		ingredienteService.save(i5); //non visualizzo relazione su tabella ponte
		System.out.println(i5);
		System.out.println(i5.getPizzas());  //visualizzo le pizze col pomodoro (i5) dalla console ma NON SU DATABASE!!
		
		
		//----------------------
		//AUTENTICAZIONE
		Role user = new Role("USER");
		Role admin = new Role("ADMIN");
		
		
		roleServ.save(user);
		roleServ.save(admin);
		
		List<Role> roles = roleServ.findAll();
		System.err.println(roles);
		
		
		System.err.println("-----------------------");
		
		
		User u1 = new User("user", "{noop}user", user);
		User u2 = new User("admin", "{noop}admin", admin);
		
//		Set<Role> adminAndUser = new HashSet<>();
//		for(Role role : roleServ.findAll())
//			adminAndUser.add(role);
//		User u3 = new User("Anna", "{noop}pAdminUser", adminAndUser);
		
		userServ.save(u1);
		userServ.save(u2);
//		userServ.save(u3);
		
		List<User> users = userServ.findAll();
		System.err.println(users);
		
	}
}
