package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario{// Indicar que hereda de Usuario
    
    // Si en el futuro añado algo específico de Cliente, iría aquí

     public Administrador() {
        super(); // Llama al constructor sin argumentos de la clase padre (Usuario)
    }
}
