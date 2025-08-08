/**
 * 
 */
package com.example.parking.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Employee class represents the data model for estancia
 * 
 * @author Daniel Manzano Borja
 * @since 07-AGO-2025	
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Estancia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_placa")
    private Vehiculo vehiculo;

    public int calcularMinutos() {
        if (horaEntrada != null && horaSalida != null) {
            return (int) java.time.Duration.between(horaEntrada, horaSalida).toMinutes();
        }
        return 0;
    }

}
