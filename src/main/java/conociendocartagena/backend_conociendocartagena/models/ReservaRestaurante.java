package conociendocartagena.backend_conociendocartagena.models;

public class ReservaRestaurante extends Reserva{

    private int numeroPersonas;
    private String requisitosEspeciales;
    
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

    
    
}
