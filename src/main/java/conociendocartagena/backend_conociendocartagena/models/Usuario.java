package conociendocartagena.backend_conociendocartagena.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "usuarios")// Nombre de la tabla en la base de datos
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Estrategia de herencia SINGLE_TABLE
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
// En tu clase Usuario.java (si es abstracta y tiene subclases)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // O Id.CLASS, Id.PROPERTY
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo" // Nombre del campo en tu JSON que indicará el tipo de usuario
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Cliente.class, name = "cliente"),
    @JsonSubTypes.Type(value = Administrador.class, name = "admin")
    // Agrega todas tus subclases concretas aquí
})
public abstract class Usuario {
    @Id
    // <-- Generación automática del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Columna para distinguir el tipo de usuario
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String username;
    private String telefono;
    private String email;
    private String tipo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY) 
    private List<Reserva> reservas;
   
    @JsonIgnore
    private String password;

    public Usuario() {}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public List<Reserva> getReservas() {
        return reservas;
    }
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
