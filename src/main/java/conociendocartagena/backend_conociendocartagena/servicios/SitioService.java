package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Sitio;
import conociendocartagena.backend_conociendocartagena.repositorios.SitioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SitioService {

    @Autowired
    private SitioRepository sitioRepository;

    /**
     * Obtiene una lista de todos los sitios turísticos.
     * @return Una lista de objetos Sitio.
     */
    public List<Sitio> listar() {
        return sitioRepository.findAll();
    }

    /**
     * Guarda un nuevo sitio turístico o actualiza uno existente.
     * @param sitio El objeto Sitio a guardar.
     * @return El objeto Sitio guardado.
     */
    public Sitio crear(Sitio sitio) {
        // Aquí podrías añadir lógica de negocio antes de guardar,
        // por ejemplo, validaciones específicas para sitios.
        return sitioRepository.save(sitio);
    }

    /**
     * Obtiene un sitio turístico por su ID.
     * @param id El ID del sitio a obtener.
     * @return Un Optional que contiene el objeto Sitio si se encuentra, o vacío si no.
     */
    public Optional<Sitio> consultar(int id) {
        return sitioRepository.findById(id);
    }

    /**
     * Elimina un sitio turístico por su ID.
     * @param id El ID del sitio a eliminar.
     * @return true si el sitio fue eliminado, false en caso contrario.
     */
    public boolean eliminar(int id) {
        if (sitioRepository.existsById(id)) {
            sitioRepository.deleteById(id);
            return true;
        } else {
            // Es mejor lanzar una excepción aquí (ej., SitioNotFoundException)
            // para que el controlador pueda devolver un 404 Not Found.
            return false;
        }
    }

    /**
     * Actualiza un sitio turístico existente.
     * @param id El ID del sitio a actualizar.
     * @param sitioActualizado El objeto Sitio con los datos actualizados.
     * @return El objeto Sitio actualizado.
     * @throws RuntimeException si el sitio no se encuentra.
     */
    public Sitio actualizar(int id, Sitio sitioActualizado) {
        return sitioRepository.findById(id)
                .map(sitioExistente -> {
                    // Actualiza solo los campos que se deben actualizar.
                    // Por simplicidad, estamos actualizando todos los campos desde sitioActualizado.
                    // En una aplicación real, podrías usar un DTO de actualización
                    // y copiar propiedades de forma selectiva.
                    sitioExistente.setNombreSitio(sitioActualizado.getNombreSitio());
                    sitioExistente.setUbicacionSitio(sitioActualizado.getUbicacionSitio());
                    sitioExistente.setReservaRequerida(sitioActualizado.isReservaRequerida());
                    sitioExistente.setCapacidadMaxima(sitioActualizado.getCapacidadMaxima()); // ¡Importante para las reservas!
                    // Añade otros campos según sea necesario

                    return sitioRepository.save(sitioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Sitio no encontrado con ID: " + id));
    }
}
