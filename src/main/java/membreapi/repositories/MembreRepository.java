package membreapi.repositories;



import membreapi.models.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {
    boolean existsByMail(String mail);

    boolean existsByNomAndPrenom(String nom, String prenom);


    Membre findByNomAndPrenomAndIdNot(String nom, String prenom, Long membreId);

    Membre findByMail(String mail);
}
