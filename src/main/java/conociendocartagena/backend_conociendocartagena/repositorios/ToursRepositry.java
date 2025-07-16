package conociendocartagena.backend_conociendocartagena.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import conociendocartagena.backend_conociendocartagena.models.Tours;

public interface ToursRepositry extends JpaRepository<Tours, Integer> {
    
}
