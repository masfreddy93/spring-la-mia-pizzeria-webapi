package org.spring.italy.demo.repo;

import org.spring.italy.demo.pojo.Promozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromozioneRepo extends JpaRepository<Promozione, Integer>{

	
}
