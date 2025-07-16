package conociendocartagena.backend_conociendocartagena.models;

public class ReservaTours {
    private String nombreTour;
    private String duracionTour;
    private boolean guiaTour;


   
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
    
    


}
