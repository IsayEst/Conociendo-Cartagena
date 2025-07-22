package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaSitio extends Reserva{
    private int numeroPersonas;
    private boolean guia;

    @ManyToOne
    @JoinColumn(name = "idSitios")
    private Sitio sitioTuristico; // <-- Campo para la relación con SitiosTuristico

    public ReservaSitio(){

    }
    
    public int getNumeroPersonas() {
        return numeroPersonas;
    }
    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
    public boolean isGuia() {
        return guia;
    }
    public void setGuia(boolean guia) {
        this.guia = guia;
    }

    public Sitio getSitioTuristico() {
        return sitioTuristico;
    }

    public void setSitioTuristico(Sitio sitioTuristico) {
        this.sitioTuristico = sitioTuristico;
    }
    
}
