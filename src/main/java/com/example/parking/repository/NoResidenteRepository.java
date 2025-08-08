/**
 * 
 */
package com.example.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parking.model.entity.NoResidente;

/**
 * JPA-interface that contains the API no residente Repository.
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025
 * 
 */
@Repository
public interface NoResidenteRepository extends JpaRepository<NoResidente, String> {

}
