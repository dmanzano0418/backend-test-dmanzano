package com.example.parking.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejador global de excepciones para toda la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(VehiculoYaRegistradoException.class)
    public ResponseEntity<ErrorResponse> handleVehiculoYaRegistrado(
            VehiculoYaRegistradoException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(VehiculoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleVehiculoNoEncontrado(
            VehiculoNoEncontradoException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(VehiculoYaEnEstacionamientoException.class)
    public ResponseEntity<ErrorResponse> handleVehiculoYaEnEstacionamiento(
            VehiculoYaEnEstacionamientoException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(VehiculoSinEstanciaActivaException.class)
    public ResponseEntity<ErrorResponse> handleVehiculoSinEstanciaActiva(
            VehiculoSinEstanciaActivaException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EstanciaNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEstanciaNoEncontrada(
            EstanciaNoEncontradaException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Maneja la excepción personalizada ReporteGeneracionException y devuelve un JSON con código 500.
     *
     * @param ex la excepción lanzada.
     * @return respuesta con detalles del error.
     */
    @ExceptionHandler(ReporteGeneracionException.class)
    public ResponseEntity<Map<String, Object>> handleReporteGeneracionException(ReporteGeneracionException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Error en generación de reporte");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception ex, HttpStatus status, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, status);
    }
	
}
