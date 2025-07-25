package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Usuario; // <-- CORREGIDO: 'models' en lugar de 'model'
import conociendocartagena.backend_conociendocartagena.repositorios.UsuarioRepository; // <-- CORREGIDO: 'repositories' en lugar de 'repository'

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        return new User(usuario.getUsername(), usuario.getPassword(), Collections.emptyList());
    }
}
