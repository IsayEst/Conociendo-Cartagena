package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaRestaurante extends Reserva{

    private int numeroPersonas;
    private String requisitosEspeciales;

    @ManyToOne
    @JoinColumn(name = "idRestaurante")
    private Restaurante restaurante; // <-- Campo para la relación con Restaurante

    public ReservaRestaurante() { 
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }
    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
    public String getRequisitosEspeciales() {
        return requisitosEspeciales;
    }
    public void setRequisitosEspeciales(String requisitosEspeciales) {
        this.requisitosEspeciales = requisitosEspeciales;
    }
    public Restaurante getRestaurante() {
        return restaurante;
    }
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    
    
}
