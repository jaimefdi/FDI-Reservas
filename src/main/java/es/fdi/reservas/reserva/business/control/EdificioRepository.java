package es.fdi.reservas.reserva.business.control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Edificio;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, Long>{

}
