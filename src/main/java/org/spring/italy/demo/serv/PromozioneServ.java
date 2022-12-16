package org.spring.italy.demo.serv;

import java.util.List;

import org.hibernate.Hibernate;
import org.spring.italy.demo.pojo.Promozione;
import org.spring.italy.demo.repo.PromozioneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PromozioneServ {

	@Autowired
	private PromozioneRepo promozioneRepo;
	
	//methods
	public void save(Promozione promo) {
		
		promozioneRepo.save(promo);
	}
	
	public void delete(Promozione promo) {
		
		promozioneRepo.delete(promo);
	}
	
	public Promozione findById(int id) {
		
		return promozioneRepo.findById(id).get();
	}
	
	public List<Promozione> findAll(){
		
		return promozioneRepo.findAll();
	}
	
	@Transactional
	public List<Promozione> findAllWPizza(){
		
		List<Promozione> promotions = promozioneRepo.findAll();
		
		for(Promozione promo : promotions) {
			
			Hibernate.initialize(promo.getListOfPizzas());
		}
		
		return promotions;
	}
}
