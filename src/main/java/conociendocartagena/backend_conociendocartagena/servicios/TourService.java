package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Tour;
import conociendocartagena.backend_conociendocartagena.repositorios.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    /**
     * Obtiene una lista de todos los tours.
     * @return Una lista de objetos Tour.
     */
    public List<Tour> listar() {
        return tourRepository.findAll();
    }

    /**
     * Guarda un nuevo tour o actualiza uno existente.
     * @param tour El objeto Tour a guardar.
     * @return El objeto Tour guardado.
     */
    public Tour crear(Tour tour) {
        // Aquí podrías añadir lógica de negocio antes de guardar,
        // por ejemplo, validaciones específicas para tours.
        return tourRepository.save(tour);
    }

    /**
     * Obtiene un tour por su ID.
     * @param id El ID del tour a obtener.
     * @return Un Optional que contiene el objeto Tour si se encuentra, o vacío si no.
     */
    public Optional<Tour> consultar(int id) {
        return tourRepository.findById(id);
    }

    /**
     * Elimina un tour por su ID.
     * @param id El ID del tour a eliminar.
     * @return true si el tour fue eliminado, false en caso contrario.
     */
    public boolean eliminar(int id) {
        if (tourRepository.existsById(id)) {
            tourRepository.deleteById(id);
            return true;
        } else {
            // Es mejor lanzar una excepción aquí (ej., TourNotFoundException)
            // para que el controlador pueda devolver un 404 Not Found.
            return false;
        }
    }

    /**
     * Actualiza un tour existente.
     * @param id El ID del tour a actualizar.
     * @param tourActualizado El objeto Tour con los datos actualizados.
     * @return El objeto Tour actualizado.
     * @throws RuntimeException si el tour no se encuentra.
     */
    public Tour actualizar(int id, Tour tourActualizado) {
        return tourRepository.findById(id)
                .map(tourExistente -> {
                    // Actualiza solo los campos que se deben actualizar.
                    // Por simplicidad, estamos actualizando todos los campos desde tourActualizado.
                    // En una aplicación real, podrías usar un DTO de actualización
                    // y copiar propiedades de forma selectiva.
                    tourExistente.setNombreTour(tourActualizado.getNombreTour());
                    tourExistente.setUbicacionTour(tourActualizado.getUbicacionTour());
                    tourExistente.setTarifaTour(tourActualizado.getTarifaTour()); // Asumo que tienes un campo tarifa
                    tourExistente.setCapacidadMaxima(tourActualizado.getCapacidadMaxima()); // ¡Importante para las reservas!
                    // Añade otros campos según sea necesario

                    return tourRepository.save(tourExistente);
                })
                .orElseThrow(() -> new RuntimeException("Tour no encontrado con ID: " + id));
    }
}
