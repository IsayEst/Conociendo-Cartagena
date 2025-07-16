package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaSitios extends Reserva{
    private int numeroPersonas;
    private boolean guia;

    @ManyToOne
    @JoinColumn(name = "idSitios")
    private Sitios sitioTuristico; // <-- Campo para la relación con SitiosTuristico

    public ReservaSitios(){

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

    public Sitios getSitioTuristico() {
        return sitioTuristico;
    }

    public void setSitioTuristico(Sitios sitioTuristico) {
        this.sitioTuristico = sitioTuristico;
    }
    
}
