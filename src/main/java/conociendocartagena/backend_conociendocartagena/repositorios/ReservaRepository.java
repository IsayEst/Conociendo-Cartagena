package conociendocartagena.backend_conociendocartagena.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import conociendocartagena.backend_conociendocartagena.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Spring Data JPA generará automáticamente métodos CRUD básicos como:
    // - save(Reserva entity)
    // - findById(Long id)
    // - findAll()
    // - delete(Reserva entity)
    // ... y muchos más
    
    // Aquí podrías añadir métodos personalizados si los necesitas, por ejemplo:
    // List<Reserva> findByUsuario(Usuario usuario);
    // List<Reserva> findByFechaBetween(LocalDate startDate, LocalDate endDate);
} 
