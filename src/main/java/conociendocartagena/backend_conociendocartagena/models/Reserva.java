package conociendocartagena.backend_conociendocartagena.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jakarta.persistence.InheritanceType; // Importar InheritanceType
import jakarta.persistence.DiscriminatorType;


@Entity
@Table(name = "reservas") // Nombre de la tabla en la base de datos
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Estrategia de herencia simple
@DiscriminatorColumn(name = "tipo_reserva", discriminatorType = DiscriminatorType.STRING) // Columna para distinguir el tipo de reserva
public abstract class Reserva {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int idReserva;
    private java.time.LocalDate fecha;
    private String hora;
    private String estado;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY) //evita cargar el objeto Usuario de la base de datos cada vez que cargas una Reserva. Esto mejora el rendimiento
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFecha() { // Cambiado a LocalDate
        return fecha;
    }

    public void setFecha(LocalDate fecha) { // Cambiado a LocalDate
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() { // Cambiado a LocalDateTime
        return fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
