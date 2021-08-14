package ar.com.ada.api.aladas.repos;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.aladas.entities.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

    Reserva findByReservaId(Integer id);
    
}
