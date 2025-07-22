package conociendocartagena.backend_conociendocartagena.repositorios;

import conociendocartagena.backend_conociendocartagena.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Método para contar reservas para un restaurante en una fecha específica
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.restaurante.idRestaurante = :restauranteId AND r.fecha = :fecha")
    Long countByRestauranteIdAndFecha(@Param("restauranteId") Integer restauranteId, @Param("fecha") LocalDate fecha);

    // Método para contar reservas para un sitio en una fecha específica
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.sitio.idSitio = :sitioId AND r.fecha = :fecha")
    Long countBySitioIdAndFecha(@Param("sitioId") Integer sitioId, @Param("fecha") LocalDate fecha);

    // Método para contar reservas para un tour en una fecha específica
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.tour.idTour = :tourId AND r.fecha = :fecha")
    Long countByTourIdAndFecha(@Param("tourId") Integer tourId, @Param("fecha") LocalDate fecha);
}