package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaTour extends Reserva {
    private String nombreTour;
    private String duracionTour;
    private boolean guiaTour;

    @ManyToOne
    @JoinColumn(name = "idTuor")
    private Tour tour; // <-- Campo para la relación con Tours

    public ReservaTour(){

    }
    
    public String getNombreTour() {
        return nombreTour;
    }
    public void setNombreTour(String nombreTour) {
        this.nombreTour = nombreTour;
    }
    public String getDuracionTour() {
        return duracionTour;
    }
    public void setDuracionTour(String duracionTour) {
        this.duracionTour = duracionTour;
    }
    public boolean isGuiaTour() {
        return guiaTour;
    }
    public void setGuiaTour(boolean guiaTour) {
        this.guiaTour = guiaTour;
    }
    
     public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
    


}
