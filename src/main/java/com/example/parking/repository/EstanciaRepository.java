/**
 * 
 */
package com.example.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parking.model.entity.Estancia;

/**
 * JPA-interface that contains the API estancia Repository.
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 * 
 */
@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	
	List<Estancia> findByVehiculo_NumeroPlaca(String numeroPlaca);
	
	List<Estancia> findByHoraSalidaIsNull();

}
