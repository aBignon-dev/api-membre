package membreapi.repositories;

import membreapi.models.Inscription;
import membreapi.models.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findByEvenementId(Long evenementId);
    boolean existsByMembreIdAndEvenementId(Long membreId, Long evenementId);

}
