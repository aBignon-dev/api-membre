package membreapi.services;

import membreapi.models.Membre;
import membreapi.repositories.MembreRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {

    private final MembreRepository membreRepository;

    public DeleteService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    public void deleteMembre(Long membreId) {
        // Vérifier si le membre existe dans la base de données
        Membre existingMembre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable avec l'ID: " + membreId));

        // Supprimer le membre de la base de données
        membreRepository.delete(existingMembre);
    }
}
