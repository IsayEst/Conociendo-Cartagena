package conociendocartagena.backend_conociendocartagena.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sitios {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSitios;
    private String nombreSitio;
    private String ubicacionSitio;
    private boolean reservaRequerida;
    private String horarioAtencion;
    private int capacidadMaxima;

    @OneToMany(mappedBy = "sitioTuristico")
    private List<ReservaSitios> reservasSitios = new ArrayList<>();

       // Constructores, Getters y Setters
    public Sitios() {
    }
    public int getIdSitios() {
        return idSitios;
    }
    public void setIdSitios(int idSitios) {
        this.idSitios = idSitios;
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
