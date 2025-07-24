package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ¡Inyectar el PasswordEncoder!

    /**
     * Retrieves a list of all users.
     * @return A list of Usuario objects.
     */
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * @param usuario The Usuario object to save.
     * @return The saved Usuario object.
     */
    public Usuario crear(Usuario usuario) {
        // Antes de guardar, hashear la contraseña
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);
  
        return usuarioRepository.save(usuario);
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the Usuario object if found, or empty if not.
     */
    public Optional<Usuario> consultar(int id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     * @return true if the user was deleted, false otherwise.
     */
    public boolean eliminar(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            // It's often better to throw an exception here (e.g., UserNotFoundException)
            // so the controller can return a 404 Not Found.
            return false;
        }
    }

    /**
     * Actualizar usuario existente.
     * @param id Id usuario a actualizar.
     * @param usuarioActualizado El usuario actualizado.
     * @return El usuario actualizado.
     * @throws RuntimeException if the user is not found.
     */
    public Usuario actualizar(int id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    // Update only the fields that are meant to be updated.
                    // For simplicity, we're updating all fields from usuarioActualizado.
                    // In a real application, you might use a DTO for updates
                    // and selectively copy properties.
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                    usuarioExistente.setUsername(usuarioActualizado.getUsername());
                    usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
                    usuarioExistente.setEmail(usuarioActualizado.getEmail());
                    usuarioExistente.setTipo(usuarioActualizado.getTipo());
                    // Add other fields as needed
                    // *** LÓGICA PARA LA CONTRASEÑA ***
                    // Solo hashear y actualizar la contraseña si se proporciona una nueva
                    // y no está vacía en el objeto de actualización.
                    // Esto evita sobrescribir un hash válido con un valor nulo o vacío
                    // si la contraseña no se envía en la solicitud de actualización.
                    if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                        String hashedPassword = passwordEncoder.encode(usuarioActualizado.getPassword());
                        usuarioExistente.setPassword(hashedPassword);
                    }
                    // Si usuarioActualizado.getPassword() es null o vacío,
                    // la contraseña existente en usuarioExistente se mantiene sin cambios.
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}
