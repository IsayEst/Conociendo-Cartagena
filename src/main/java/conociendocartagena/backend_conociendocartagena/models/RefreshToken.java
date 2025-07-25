package conociendocartagena.backend_conociendocartagena.models;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_tokens") // Nombre de la tabla en la base de datos
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Usa Long para IDs autoincrementales en JPA, es el tipo recomendado

    @Column(nullable = false, unique = true) // El token debe ser único y no nulo
    private String token;

    @Column(nullable = false) // La fecha de expiración no debe ser nula
    private LocalDateTime expiryDate; // Usa Instant para timestamps, es inmutable y más moderno que Date

    // Relación ManyToOne: Muchos RefreshTokens pueden pertenecer a UN Usuario
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading es una buena práctica para relaciones ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Define la columna de la clave foránea en la tabla refresh_tokens
    private Usuario usuario; // Un RefreshToken pertenece a un Usuario

    // --- IMPORTANTE: Añade constructores, getters y setters ---
    // JPA necesita un constructor sin argumentos
    public RefreshToken() {
    }

    // Constructor con campos para facilitar la creación
    public RefreshToken(String token, LocalDateTime expiryDate, Usuario usuario ) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.usuario = usuario;
        
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
