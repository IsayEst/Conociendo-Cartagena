package conociendocartagena.backend_conociendocartagena.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tours {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTuor;
    private String nombreTuor;
    private String ubicacionTuor;
    private String tipoTuor;
    private String horarioDisponible;
    private double tarifaTuor;
    private boolean reservaRequerida;

    @OneToMany(mappedBy = "tour") // "tour" es el nombre del campo en ReservaTours
    private List<ReservaTours> reservasTours = new ArrayList<>();

     // Constructores, Getters y Setters
    public Tours() {
    }
    
    public int getIdTuor() {
        return idTuor;
    }
    public void setIdTuor(int idTuor) {
        this.idTuor = idTuor;
    }
    public String getNombreTuor() {
        return nombreTuor;
    }
    public void setNombreTuor(String nombreTuor) {
        this.nombreTuor = nombreTuor;
    }
    public String getUbicacionTuor() {
        return ubicacionTuor;
    }
    public void setUbicacionTuor(String ubicacionTuor) {
        this.ubicacionTuor = ubicacionTuor;
    }
    public String getTipoTuor() {
        return tipoTuor;
    }
    public void setTipoTuor(String tipoTuor) {
        this.tipoTuor = tipoTuor;
    }
    public String getHorarioDisponible() {
        return horarioDisponible;
    }
    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }
    public double getTarifaTuor() {
        return tarifaTuor;
    }
    public void setTarifaTuor(double tarifaTuor) {
        this.tarifaTuor = tarifaTuor;
    }
    public boolean isReservaRequerida() {
        return reservaRequerida;
    }
    public void setReservaRequerida(boolean reservaRequerida) {
        this.reservaRequerida = reservaRequerida;
    }

    
}
