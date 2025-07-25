package conociendocartagena.backend_conociendocartagena.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conociendocartagena.backend_conociendocartagena.models.RefreshToken;
import conociendocartagena.backend_conociendocartagena.models.Usuario;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUsuario(Usuario usuario);   

    
} 