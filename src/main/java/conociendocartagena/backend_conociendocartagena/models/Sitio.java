package conociendocartagena.backend_conociendocartagena.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType; // Importar para operaciones en cascada
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sitio {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSitio;
    private String nombreSitio;
    private String ubicacionSitio;
    private boolean reservaRequerida;
    private String horarioAtencion;
    private int capacidadMaxima;

    @OneToMany(mappedBy = "sitio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaSitio> reservasSitios = new ArrayList<>();

    /*@OneToMany(mappedBy = "reserva")
    private List<Reserva> reservas = new ArrayList<>();*/

       // Constructores, Getters y Setters
    public Sitio() {
    }
    public int getIdSitio() {
        return idSitio;
    }
    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }
    public String getNombreSitio() {
        return nombreSitio;
    }
    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }
    public String getUbicacionSitio() {
        return ubicacionSitio;
    }
    public void setUbicacionSitio(String ubicacionSitio) {
        this.ubicacionSitio = ubicacionSitio;
    }
    public boolean isReservaRequerida() {
        return reservaRequerida;
    }
    public void setReservaRequerida(boolean reservaRequerida) {
        this.reservaRequerida = reservaRequerida;
    }
    public String getHorarioAtencion() {
        return horarioAtencion;
    }
    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
    

    
}
