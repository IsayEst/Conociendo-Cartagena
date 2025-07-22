package conociendocartagena.backend_conociendocartagena.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin") // Este valor se guardará en la columna 'tipo_usuario' de la tabla 'usuarios'
public class Administrador extends Usuario{// Indicar que hereda de Usuario
    
    // Si en el futuro añado algo específico de Cliente, iría aquí

     public Administrador() {
        super(); // Llama al constructor sin argumentos de la clase padre (Usuario)
        this.setTipo("admin");
    }
}
