package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Retrieves a list of all users.
     * @return A list of Usuario objects.
     */
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * Saves a new user or updates an existing one.
     * @param usuario The Usuario object to save.
     * @return The saved Usuario object.
     */
    public Usuario crear(Usuario usuario) {
        // Here you could add business logic before saving,
        // e.g., encrypting password, validating email format, etc.
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
     * Updates an existing user.
     * @param id The ID of the user to update.
     * @param usuarioActualizado The Usuario object with updated data.
     * @return The updated Usuario object.
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

                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}
