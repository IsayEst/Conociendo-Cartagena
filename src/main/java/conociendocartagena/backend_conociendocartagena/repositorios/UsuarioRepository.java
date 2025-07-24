package conociendocartagena.backend_conociendocartagena.repositorios;



import org.springframework.data.jpa.repository.JpaRepository;

import conociendocartagena.backend_conociendocartagena.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  
}