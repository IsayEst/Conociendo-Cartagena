package conociendocartagena.backend_conociendocartagena.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tour {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTour;
    private String nombreTour;
    private String ubicacionTour;
    private String tipoTour;
    private String horarioDisponible;
    private double tarifaTour;
    private boolean reservaRequerida;
    private int capacidadMaxima;

    @OneToMany(mappedBy = "tour") // "tour" es el nombre del campo en ReservaTours
    private List<ReservaTour> reservasTours = new ArrayList<>();

    public List<ReservaTour> getReservasTours() {
        return reservasTours;
    }

    public void setReservasTours(List<ReservaTour> reservasTours) {
        this.reservasTours = reservasTours;
    }
    @OneToMany(mappedBy = "reserva")
    private List<Reserva> reservas = new ArrayList<>();
    
     // Constructores, Getters y Setters
    public Tour() {
    }
    
    public int getIdTour() {
        return idTour;
    }
    public void setIdTour(int idTour) {
        this.idTour = idTour;
    }
    public String getNombreTour() {
        return nombreTour;
    }
    public void setNombreTour(String nombreTour) {
        this.nombreTour = nombreTour;
    }
    public String getUbicacionTour() {
        return ubicacionTour;
    }
    public void setUbicacionTour(String ubicacionTour) {
        this.ubicacionTour = ubicacionTour;
    }
    public String getTipoTour() {
        return tipoTour;
    }
    public void setTipoTour(String tipoTour) {
        this.tipoTour = tipoTour;
    }
    public String getHorarioDisponible() {
        return horarioDisponible;
    }
    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }
    public double getTarifaTour() {
        return tarifaTour;
    }
    public void setTarifaTour(double tarifaTour) {
        this.tarifaTour = tarifaTour;
    }
    public boolean isReservaRequerida() {
        return reservaRequerida;
    }
    public void setReservaRequerida(boolean reservaRequerida) {
        this.reservaRequerida = reservaRequerida;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    
}
