package org.spring.italy.demo.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Ingrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@ManyToMany(mappedBy = "ingredients")
	@JsonIgnore
	private List<Pizza> pizzas;
	
	public Ingrediente() {}
	public Ingrediente(String name) {
		
		setName(name);
	}
	
	public Ingrediente(String name, List<Pizza> pizzas) {
		
		this(name);    //N.B. usare qst scrittura per NON RIPETERE CODICE (chiamare costruttore sopra) //BE DRY!
		setPizzas(pizzas);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	
	@PreRemove
	private void removeIngredientFromPizzas() {
	    for (Pizza p : getPizzas())
	        p.getIngredients().remove(this);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId()
				+ " - " + getName();
	}
	
}
