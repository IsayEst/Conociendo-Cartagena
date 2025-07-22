package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Restaurante;
import conociendocartagena.backend_conociendocartagena.repositorios.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Obtiene una lista de todos los restaurantes.
     * @return Una lista de objetos Restaurante.
     */
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    /**
     * Guarda un nuevo restaurante o actualiza uno existente.
     * @param restaurante El objeto Restaurante a guardar.
     * @return El objeto Restaurante guardado.
     */
    public Restaurante crear(Restaurante restaurante) {
        // Aquí podrías añadir lógica de negocio antes de guardar,
        // por ejemplo, validaciones específicas para restaurantes.
        return restauranteRepository.save(restaurante);
    }

    /**
     * Obtiene un restaurante por su ID.
     * @param id El ID del restaurante a obtener.
     * @return Un Optional que contiene el objeto Restaurante si se encuentra, o vacío si no.
     */
    public Optional<Restaurante> consultar(int id) {
        return restauranteRepository.findById(id);
    }

    /**
     * Elimina un restaurante por su ID.
     * @param id El ID del restaurante a eliminar.
     * @return true si el restaurante fue eliminado, false en caso contrario.
     */
    public boolean eliminar(int id) {
        if (restauranteRepository.existsById(id)) {
            restauranteRepository.deleteById(id);
            return true;
        } else {
            // Es mejor lanzar una excepción aquí (ej., RestauranteNotFoundException)
            // para que el controlador pueda devolver un 404 Not Found.
            return false;
        }
    }

    /**
     * Actualiza un restaurante existente.
     * @param id El ID del restaurante a actualizar.
     * @param restauranteActualizado El objeto Restaurante con los datos actualizados.
     * @return El objeto Restaurante actualizado.
     * @throws RuntimeException si el restaurante no se encuentra.
     */
    public Restaurante actualizar(int id, Restaurante restauranteActualizado) {
        return restauranteRepository.findById(id)
                .map(restauranteExistente -> {
                    // Actualiza solo los campos que se deben actualizar.
                    // Por simplicidad, estamos actualizando todos los campos desde restauranteActualizado.
                    // En una aplicación real, podrías usar un DTO de actualización
                    // y copiar propiedades de forma selectiva.
                    restauranteExistente.setNombre(restauranteActualizado.getNombre());
                    restauranteExistente.setUbicacion(restauranteActualizado.getUbicacion());
                    restauranteExistente.setTelefono(restauranteActualizado.getTelefono());
                    restauranteExistente.setParqueadero(restauranteActualizado.isParqueadero());
                    restauranteExistente.setCapacidadMaxima(restauranteActualizado.getCapacidadMaxima()); // ¡Importante para las reservas!
                    // Añade otros campos según sea necesario

                    return restauranteRepository.save(restauranteExistente);
                })
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + id));
    }
}
