/**
 * 
 */
package com.example.parking.model.entity;

import java.io.Serializable;

import com.example.parking.model.enums.TipoVehiculo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un vehículo en el sistema de estacionamiento.
 * Contiene un identificador interno (id), la placa única y el tipo de vehículo.
 * Además, almacena el tiempo acumulado en minutos para vehículos residentes.
 * 
 * @author Daniel Manzano Borja
 * @since 2025-08-08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehiculos", uniqueConstraints = {
    @UniqueConstraint(columnNames = "placa", name = "uk_vehiculo_placa")
})
public class Vehiculo implements Serializable {
	
	private static final long serialVersionUID = -540219530252019084L;

	/**
     * Identificador único interno generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número de placa del vehículo, debe ser único y no nulo.
     */
    @Column(length = 20, nullable = false, unique = true)
    private String placa;

    /**
     * Tipo de vehículo (OFICIAL, RESIDENTE, NO_RESIDENTE, etc.).
     */
	 @Enumerated(EnumType.STRING)
	 @Column(nullable = false)
	 private TipoVehiculo tipo;
	 
	 /**
	  * Tiempo acumulado en minutos para vehículos residentes.
	  * Para otros tipos usualmente es cero.
	  */
	 @Builder.Default
	 @Column(name = "tiempo_acumulado")
	 private Long tiempoAcumulado = 0L;
	 
	 /**
	     * Acumula minutos al tiempo acumulado, útil para vehículos residentes.
	     * 
	     * @param minutos minutos a acumular
	     */
	 public void acumularTiempo(long minutos) {
		 if (this.tiempoAcumulado == null) {
			 this.tiempoAcumulado = 0L;
		 }
		 this.tiempoAcumulado += minutos;
	 }

}
