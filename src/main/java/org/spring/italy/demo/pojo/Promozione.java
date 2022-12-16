package org.spring.italy.demo.pojo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Promozione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "promo", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Pizza> listOfPizzas;
	
	@NotNull(message = "La data di inizio non può essere nulla")
	private LocalDate dataInzio;
	
	@NotNull(message = "La data di inizio non può essere nulla")
	private LocalDate dataFine;
	
	@NotNull(message = "Il titolo non può essere nullo")
	@NotBlank(message = "Il titolo non può essere nullo")
	@Column(unique = true)
	private String titolo;

	public Promozione() {}
	
	public Promozione(LocalDate dataInizio, LocalDate dataFine, String titolo) {
		
		setDataInizio(dataInizio);
		setDataFine(dataFine);
		setTitolo(titolo);		
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Pizza> getListOfPizzas() {
		return listOfPizzas;
	}

	public void setListOfPizzas(List<Pizza> listOfPizzas) {
		this.listOfPizzas = listOfPizzas;
	}

	public LocalDate getDataInizio() {
		return dataInzio;
	}

	public void setDataInizio(LocalDate dataInzio) {
		this.dataInzio = dataInzio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + id + ")" 
				+ " " + titolo
				+ "\n " + dataInzio
				+ " - " + dataFine;
	}
	
}
