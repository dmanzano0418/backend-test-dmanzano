/**
 * 
 */
package com.example.parking.model.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import com.example.parking.model.enums.TipoVehiculo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una estancia de un vehículo en el estacionamiento.
 * Guarda la hora de entrada, salida y el importe cobrado (si aplica).
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
@Table(name = "estancias")
public class Estancia implements Serializable {
	
	private static final long serialVersionUID = 2240020541071831557L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "hora_entrada", nullable = false)
    private LocalDateTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculo_id", referencedColumnName = "id")
    private Vehiculo vehiculo;

    @Column(name = "importe_pago")
    private Double importePago;
    
    /**
     * Calcula la duración en minutos entre la hora de entrada y la hora de salida.
     * 
     * @return duración en minutos, 0 si alguna hora es null o salida es antes que entrada
     */
    public long getDuracionMinutos() {
        if (horaEntrada != null && horaSalida != null && !horaSalida.isBefore(horaEntrada)) {
            return Duration.between(horaEntrada, horaSalida).toMinutes();
        }
        return 0;
    }

    /**
     * Calcula y actualiza el importe a pagar según el tipo de vehículo y duración.
     * Vehículos oficiales no pagan.
     * Residentes no pagan al salir (se acumula tiempo).
     * No residentes pagan MXN 0.5 por minuto.
     * 
     * @return importe calculado o 0 si no aplica pago
     */
    public double calcularImportePago() {
        if (vehiculo == null || horaEntrada == null || horaSalida == null) {
            return 0.0;
        }

        TipoVehiculo tipo = vehiculo.getTipo();

        long minutos = getDuracionMinutos();
        switch (tipo) {
            case OFICIAL:
                importePago = 0.0;
                break;
            case RESIDENTE:
                importePago = 0.0;
                break;
            case NO_RESIDENTE:
                importePago = minutos * 0.5;
                break;
            default:
                importePago = 0.0;
                break;
        }
        return importePago;
    }

}
