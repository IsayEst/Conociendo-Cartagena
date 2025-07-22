package conociendocartagena.backend_conociendocartagena.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import conociendocartagena.backend_conociendocartagena.models.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    
}
