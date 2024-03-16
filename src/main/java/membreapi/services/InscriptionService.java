package membreapi.services;

import membreapi.models.Inscription;
import membreapi.models.Membre;
import membreapi.repositories.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    public Inscription addMembreToEvenement(Long membreId, Long evenementId) {
        Inscription inscription = new Inscription(membreId, evenementId);
        return inscriptionRepository.save(inscription);
    }


    public void deleteInscription(Long id) {
        Inscription existingInscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription introuvable avec l'ID: " + id));

        // Supprimer de la base de données
        inscriptionRepository.delete(existingInscription);
    }

}
