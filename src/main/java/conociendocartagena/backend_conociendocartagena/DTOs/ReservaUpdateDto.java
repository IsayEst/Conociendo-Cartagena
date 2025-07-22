package conociendocartagena.backend_conociendocartagena.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

import conociendocartagena.backend_conociendocartagena.models.EstadoReserva;// Importar el enum


// DTO para manejar la actualización de una reserva existente
public class ReservaUpdateDto {
    private LocalDate fecha;
    private LocalTime hora;
    private Integer cantidadPersonas; // Usar Integer para que pueda ser null si no se actualiza
    private EstadoReserva estado; // Permitir actualizar el estado de la reserva

    // En un DTO de actualización, generalmente no se actualizan los IDs de usuario o recurso
    // ya que eso implicaría cambiar la reserva a otro usuario o recurso, lo cual
    // suele ser una operación diferente o más compleja.
    // Si necesitas cambiar el usuario o el recurso, considera un endpoint específico para ello.

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
