package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Edificio;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, Long>{

	public List<Edificio> findByFacultad_Id(Long id_facul);
}
