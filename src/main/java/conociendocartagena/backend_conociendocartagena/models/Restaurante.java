package conociendocartagena.backend_conociendocartagena.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Restaurante {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRestaurante;
    private String nombre;
    private String ubicacion;
    private boolean reservaRequerida;
    private String tipoComida;
    private boolean parqueadero;
    private String horarioAtencion;
    private int capacidad;

    @OneToMany(mappedBy = "restaurante")
    private List<ReservaRestaurante> reservasRestaurante = new ArrayList<>(); // Inicializar para evitar NullPointerException

     // Constructores, Getters y Setters
    public Restaurante() {
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }
    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public boolean isReservaRequerida() {
        return reservaRequerida;
    }
    public void setReservaRequerida(boolean reservaRequerida) {
        this.reservaRequerida = reservaRequerida;
    }
    public String getTipoComida() {
        return tipoComida;
    }
    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }
    public boolean isParqueadero() {
        return parqueadero;
    }
    public void setParqueadero(boolean parqueadero) {
        this.parqueadero = parqueadero;
    }
    public String getHorarioAtencion() {
        return horarioAtencion;
    }
    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

        // En Restaurante.java
    public void addReservaRestaurante(ReservaRestaurante reserva) {
        this.reservasRestaurante.add(reserva);
        reserva.setRestaurante(this); // Importante para que el lado ManyToOne también se actualice
    }

    public void removeReservaRestaurante(ReservaRestaurante reserva) {
        this.reservasRestaurante.remove(reserva);
        reserva.setRestaurante(null); // Importante para romper el vínculo
    }

    
}
