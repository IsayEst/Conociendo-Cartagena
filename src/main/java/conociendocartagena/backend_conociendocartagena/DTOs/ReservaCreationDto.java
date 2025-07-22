package conociendocartagena.backend_conociendocartagena.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservaCreationDto {
    private LocalDate fecha; // O LocalDate
    private LocalTime hora;  // O LocalTime
    private int idUsuario; // Solo el ID del usuario
    private Integer cantidadPersonas;

    // IDs de los recursos a reservar (solo uno de estos debe ser no nulo)
    private Integer idRestaurante;
    private Integer idSitio; // Añadido para sitios
    private Integer idTour;    // Añadido para tours


    // Otros campos necesarios para la reserva (ej. notas, etc.)

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() { // Getter para LocalTime
        return hora;
    }

    public void setHora(LocalTime hora) { // Setter para LocalTime
        this.hora = hora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Integer getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(Integer idSitio) {
        this.idSitio = idSitio;
    }

    public Integer getIdTour() {
        return idTour;
    }

    public void setIdTour(Integer idTour) {
        this.idTour = idTour;
    }

}