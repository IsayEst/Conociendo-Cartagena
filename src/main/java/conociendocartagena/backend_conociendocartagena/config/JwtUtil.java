package conociendocartagena.backend_conociendocartagena.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;



@Component
public class JwtUtil {
    
    private static final String SECRET = "123456789123456789123456789123456789123456789123456789987654321isayest"; // Reemplaza con tu clave secreta
    private SecretKey key; 
        
        @PostConstruct
        public void init() {
           key = Keys.hmacShaKeyFor(SECRET.getBytes());
        }
    // Generar un token JWT para un usuario
    public String generateToken(String username, String nombre, String apellido) {
        return Jwts.builder()
                .setSubject(username)
                .claim("nombre", nombre)
                .claim("apellido", apellido)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira en 1 hora
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    // Extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    // Validar el token completo
    public boolean validateToken(String token) {
        try{
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Verifica si el token ha expirado
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    
    

}
