package conociendocartagena.backend_conociendocartagena.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key; // Importa Key

@Component
public class JwtUtil {
    
    private final String SECRET_KEY = "mi_clave_secreta";
    private Key getSigningKey() {
        // Decodifica la cadena SECRET_KEY_STRING (asumiendo que está en formato Base64)
        // O simplemente convierte los bytes de la cadena directamente si no es Base64 (menos seguro para secretos cortos)
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // <-- Si tu SECRET_KEY es Base64
        // byte[] keyBytes = SECRET_KEY_STRING.getBytes(); // <-- Si tu SECRET_KEY no es Base64 (menos recomendado)

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
       
        long expirationTimeMillis = System.currentTimeMillis() + 1000 * 60 * 60; // 1 hora de expiración
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return false; // Si se puede analizar, no ha expirado
        } catch (Exception e) {
            return true; // Si hay un error, ha expirado
        }
    }

    

}
