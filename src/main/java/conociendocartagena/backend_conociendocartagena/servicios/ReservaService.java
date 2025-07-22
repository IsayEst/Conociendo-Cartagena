package conociendocartagena.backend_conociendocartagena.servicios;

import conociendocartagena.backend_conociendocartagena.models.Reserva;
import conociendocartagena.backend_conociendocartagena.models.Usuario;
import conociendocartagena.backend_conociendocartagena.models.Restaurante;
import conociendocartagena.backend_conociendocartagena.models.Sitio; // Importar Sitio (singular)
import conociendocartagena.backend_conociendocartagena.models.Tour;     // Importar Tour (singular)
import conociendocartagena.backend_conociendocartagena.models.ReservaRestaurante; // Importar subclase concreta
import conociendocartagena.backend_conociendocartagena.models.ReservaSitio;     // Importar subclase concreta
import conociendocartagena.backend_conociendocartagena.models.ReservaTour;       // Importar subclase concreta


import conociendocartagena.backend_conociendocartagena.repositorios.ReservaRepository;
import conociendocartagena.backend_conociendocartagena.repositorios.UsuarioRepository;
import conociendocartagena.backend_conociendocartagena.repositorios.RestauranteRepository;
import conociendocartagena.backend_conociendocartagena.repositorios.SitioRepository; // Inyectar SitioRepository
import conociendocartagena.backend_conociendocartagena.repositorios.TourRepository;     // Inyectar TourRepository (corregido el typo)

import conociendocartagena.backend_conociendocartagena.DTOs.ReservaCreationDto;
import conociendocartagena.backend_conociendocartagena.DTOs.ReservaUpdateDto; // Asumo que crearás este DTO para actualizar
import conociendocartagena.backend_conociendocartagena.models.EstadoReserva; // Importar el Enum EstadoReserva

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private SitioRepository sitioRepository; // Inyectar
    @Autowired
    private TourRepository tourRepository;     // Inyectar (corregido el typo)

    // --- Métodos CRUD básicos ---

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> consultar(int id) {
        return reservaRepository.findById(id);
    }

    public boolean eliminar(int id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        } else {
            return false; // O lanzar una excepción si prefieres
        }
    }

    // --- Método de Creación de Reserva con Lógica de Negocio ---
    public Reserva crear(ReservaCreationDto reservaDto) {
        // 1. Validar que solo un tipo de recurso (restaurante, sitio, tour) esté presente
        int recursosPresentes = 0;
        if (reservaDto.getIdRestaurante() != null) recursosPresentes++;
        if (reservaDto.getIdSitio() != null) recursosPresentes++; // Corregido a getIdSitio()
        if (reservaDto.getIdTour() != null) recursosPresentes++;

        if (recursosPresentes != 1) {
            throw new RuntimeException("Debe especificar exactamente un recurso (restaurante, sitio o tour) para la reserva.");
        }

        // 2. Buscar y validar la existencia del Usuario
        Usuario usuario = usuarioRepository.findById(reservaDto.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + reservaDto.getIdUsuario()));

        LocalDate fechaReserva = reservaDto.getFecha(); // Ya es LocalDate
        Integer capacidadMaxima = null;
        String nombreRecurso = ""; // Para mensajes de error

        // 3. Buscar y validar la existencia del Recurso (Restaurante, Sitio o Tour)
        // Y aplicar la lógica de capacidad máxima diaria, e instanciar la subclase de Reserva
        if (reservaDto.getIdRestaurante() != null) {
            Restaurante restaurante = restauranteRepository.findById(reservaDto.getIdRestaurante())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + reservaDto.getIdRestaurante()));
            ReservaRestaurante nuevaReserva = new ReservaRestaurante(); // Instanciar la subclase concreta
            nuevaReserva.setRestaurante(restaurante); // Asignar el restaurante
            capacidadMaxima = restaurante.getCapacidadMaxima(); // Asumo que Restaurante tiene este campo
            nombreRecurso = restaurante.getNombre();

            // Contar reservas existentes para este restaurante en esta fecha
            Long reservasExistentes = reservaRepository.countByRestauranteIdAndFecha(
                                            restaurante.getIdRestaurante(),
                                            fechaReserva
                                        );
            if (capacidadMaxima != null && reservasExistentes >= capacidadMaxima) {
                throw new RuntimeException("El restaurante '" + nombreRecurso + "' ha alcanzado su capacidad máxima de reservas para el día " + fechaReserva);
            }

            // Asignar propiedades comunes y guardar
            nuevaReserva.setUsuario(usuario); // Asignar el usuario
            nuevaReserva.setFecha(fechaReserva);
            nuevaReserva.setHora(reservaDto.getHora()); // Ya es LocalTime
            nuevaReserva.setCantidadPersonas(reservaDto.getCantidadPersonas());
            nuevaReserva.setEstado(EstadoReserva.PENDIENTE); // O el estado inicial que desees

            return reservaRepository.save(nuevaReserva);

        } else if (reservaDto.getIdSitio() != null) {
            Sitio sitio = sitioRepository.findById(reservaDto.getIdSitio())
                .orElseThrow(() -> new RuntimeException("Sitio no encontrado con ID: " + reservaDto.getIdSitio()));
            ReservaSitio nuevaReserva = new ReservaSitio(); // Instanciar la subclase concreta
            nuevaReserva.setSitio(sitio); // Asignar el sitio
            capacidadMaxima = sitio.getCapacidadMaxima(); // Asumo que Sitio tiene este campo
            nombreRecurso = sitio.getNombreSitio();

            // Contar reservas existentes para este sitio en esta fecha
            Long reservasExistentes = reservaRepository.countBySitioIdAndFecha(
                                            sitio.getIdSitio(),
                                            fechaReserva
                                        );
            if (capacidadMaxima != null && reservasExistentes >= capacidadMaxima) {
                throw new RuntimeException("El sitio '" + nombreRecurso + "' ha alcanzado su capacidad máxima de reservas para el día " + fechaReserva);
            }

            // Asignar propiedades comunes y guardar
            nuevaReserva.setUsuario(usuario); // Asignar el usuario
            nuevaReserva.setFecha(fechaReserva);
            nuevaReserva.setHora(reservaDto.getHora()); // Ya es LocalTime
            nuevaReserva.setCantidadPersonas(reservaDto.getCantidadPersonas());
            nuevaReserva.setEstado(EstadoReserva.PENDIENTE); // O el estado inicial que desees

            return reservaRepository.save(nuevaReserva);

        } else { // if (reservaDto.getIdTour() != null) - Este es el último caso posible debido a la validación inicial
            Tour tour = tourRepository.findById(reservaDto.getIdTour())
                .orElseThrow(() -> new RuntimeException("Tour no encontrado con ID: " + reservaDto.getIdTour()));
            ReservaTour nuevaReserva = new ReservaTour(); // Instanciar la subclase concreta
            nuevaReserva.setTour(tour); // Asignar el tour
            capacidadMaxima = tour.getCapacidadMaxima(); // Asumo que Tour tiene este campo
            nombreRecurso = tour.getNombreTour();

            // Contar reservas existentes para este tour en esta fecha
            Long reservasExistentes = reservaRepository.countByTourIdAndFecha(
                                            tour.getIdTour(),
                                            fechaReserva
                                        );
            if (capacidadMaxima != null && reservasExistentes >= capacidadMaxima) {
                throw new RuntimeException("El tour '" + nombreRecurso + "' ha alcanzado su capacidad máxima de reservas para el día " + fechaReserva);
            }

            // Asignar propiedades comunes y guardar
            nuevaReserva.setUsuario(usuario); // Asignar el usuario
            nuevaReserva.setFecha(fechaReserva);
            nuevaReserva.setHora(reservaDto.getHora()); // Ya es LocalTime
            nuevaReserva.setCantidadPersonas(reservaDto.getCantidadPersonas());
            nuevaReserva.setEstado(EstadoReserva.PENDIENTE); // O el estado inicial que desees

            return reservaRepository.save(nuevaReserva);
        }


    }

    // --- Método de Actualización de Reserva con Lógica de Negocio ---
    public Reserva actualizar(int id, ReservaUpdateDto reservaDto) { // Usar un DTO de actualización
        Reserva reservaExistente = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

        // Actualizar solo los campos permitidos o relevantes desde el DTO
        if (reservaDto.getFecha() != null) {
            reservaExistente.setFecha(reservaDto.getFecha());
        }
        if (reservaDto.getHora() != null) {
            reservaExistente.setHora(reservaDto.getHora());
        }
        if (reservaDto.getCantidadPersonas() != null) {
            reservaExistente.setCantidadPersonas(reservaDto.getCantidadPersonas());
        }
        if (reservaDto.getEstado() != null) {
            // Asegúrate de que el DTO de actualización tenga un campo para el estado
            // y que el tipo sea compatible (ej. String que luego mapeas a EstadoReserva)
            reservaExistente.setEstado(reservaDto.getEstado()); // Asumo que el DTO ya tiene EstadoReserva
        }

        // Si se actualizan fecha/hora/cantidad, podrías necesitar re-validar la disponibilidad
        // if (reservaDto.getFecha() != null || reservaDto.getHora() != null || reservaDto.getCantidadPersonas() != null) {
        //     // Lógica de re-validación de disponibilidad
        //     // Esto es más complejo ya que implica el recurso original de la reserva
        // }

        return reservaRepository.save(reservaExistente);
    }
}
