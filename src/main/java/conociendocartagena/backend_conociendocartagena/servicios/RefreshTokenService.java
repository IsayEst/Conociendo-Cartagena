package conociendocartagena.backend_conociendocartagena.servicios;

import org.springframework.stereotype.Service;

import conociendocartagena.backend_conociendocartagena.models.RefreshToken;
import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.repositorios.RefreshTokenRepository;
import conociendocartagena.backend_conociendocartagena.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RefreshTokenService {
   
    @Value("${jwt.refresh.expiration:604800000}") // 7 días como default
private long refreshTokenDuration;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RefreshToken createRefreshToken(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsuario(usuario);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusNanos(refreshTokenDuration * 1000000L)); // Convertir milisegundos a nanosegundos
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }
    @Transactional
    public void deleteByUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        refreshTokenRepository.deleteByUsuario(usuario);
    }

    public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
}
}
