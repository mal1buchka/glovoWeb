package kg.org.glovoweb.Repositories;

import kg.org.glovoweb.Models.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, String> {
}
