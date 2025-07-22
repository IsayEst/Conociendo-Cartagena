package conociendocartagena.backend_conociendocartagena.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; // Importar LocalTime
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Enumerated; // Para el Enum
import jakarta.persistence.EnumType; // Para el Enum



@Entity
@Table(name = "reservas") // Nombre de la tabla en la base de datos
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Estrategia de herencia simple
@DiscriminatorColumn(name = "tipo_reserva", discriminatorType = DiscriminatorType.STRING) // Columna para distinguir el tipo de reserva
public abstract class Reserva { // Sigue siendo abstracta

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReserva; // Mantenemos int según tu preferencia

    private LocalDate fecha;
    private LocalTime hora; // Cambiado a LocalTime

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como String en la DB
    private EstadoReserva estado; // Usando el Enum importado

    private LocalDateTime fechaCreacion; // Se inicializará en el constructor
    private Integer cantidadPersonas; // Añadido

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario") // Nombre de columna más estándar
    private Usuario usuario;

    // Relaciones con los recursos que se pueden reservar
    // Solo uno de estos será no nulo para cada reserva
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante", nullable = true) // Puede ser nulo
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sitio", nullable = true) // Puede ser nulo
    private Sitio sitio; // Asumiendo que tienes un modelo Sitio

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tour", nullable = true) // Puede ser nulo
    private Tour tour; // Asumiendo que tienes un modelo Tour

    // Constructor vacío requerido por JPA
    public Reserva() {
        this.fechaCreacion = LocalDateTime.now(); // Inicializar al crear la instancia
        this.estado = EstadoReserva.PENDIENTE; // Estado por defecto
    }

    // --- Getters y Setters ---
    public int getIdReserva() { // Mantenemos int
        return idReserva;
    }

    public void setIdReserva(int idReserva) { // Mantenemos int
        this.idReserva = idReserva;
    }

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

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Sitio getSitio() {
        return sitio;
    }

    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
