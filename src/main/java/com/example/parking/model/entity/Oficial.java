/**
 * 
 */
package com.example.parking.model.entity;

import com.example.parking.model.enums.TipoVehiculo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Oficial extends Vehiculo {
	
	@Override
	public TipoVehiculo getTipo() {
		return TipoVehiculo.OFICIAL;
	}

}
