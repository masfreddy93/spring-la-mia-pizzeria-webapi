package org.spring.italy.demo.pojo;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Pizza{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	@JsonIgnore
	private Promozione promo;
	
	@Column(name="name", nullable=false, unique = true)
	@NotEmpty(message="name cannot be null")
	private String name;
	
	@Column
	@Lob
	private String description;
	
	@ManyToMany
	@JsonIgnore
	private List<Ingrediente> ingredients;
	
	@Column
	@NotNull(message = "Price cannot be null")
	@Min(value = 1, message = "Price must be higher than 0")
	private Integer price;
	
	public Pizza() {}
	public Pizza(String name, String description, int price) {
		
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	public Pizza(Promozione promo, String name, String description, int price) {
		
		this(name, description, price);
		setPromo(promo);
	}
	public Pizza(Promozione promo, String name, String description, int price, List<Ingrediente> ingredients) {
		
		this(promo, name, description, price);
		setIngredients(ingredients);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public Promozione getPromo() {
		return promo;
	}

	public void setPromo(Promozione promo) {
		this.promo = promo;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public List<Ingrediente> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingrediente> ingredients) {
		this.ingredients = ingredients;
	}
	
	//metodo per evitare relazioni duplicate
	public void addIngredients(Ingrediente ingrediente) {
		
		boolean finded = false;
		for (Ingrediente i : getIngredients()) 
			if (i.getId() == ingrediente.getId())
				finded = true;
		
		if (!finded) 
			getIngredients().add(ingrediente);
	}
	

	@Override
	public String toString() {
		return "\n" + getId() + " - " + getName()
				+ "\n | " + getDescription()
				+ "\n | " + getPrice() + "€\n"
				+ "\n | " + getPromo()
				+ "\n | ";
	}
}
