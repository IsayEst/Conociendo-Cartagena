package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
