package membreapi.controllers;

import membreapi.ErrorResponse;
import membreapi.dto.InscriptionDTO;
import membreapi.dto.MembreDTO;
import membreapi.dto.MembreResponseDTO;
import membreapi.models.Inscription;
import membreapi.repositories.InscriptionRepository;
import membreapi.repositories.MembreRepository;
import membreapi.services.InscriptionService;
import membreapi.services.MembreService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InscriptionController {

    private final MembreService membreService;
    private final MembreRepository membreRepository; // Injection de MembreRepository
    private final InscriptionService inscriptionService;
    
    private final InscriptionRepository inscriptionRepository;
    public InscriptionController(MembreService membreService, MembreRepository membreRepository, InscriptionService inscriptionService, InscriptionRepository inscriptionRepository) {
        this.membreService = membreService;
        this.membreRepository = membreRepository; // Initialisation de MembreRepository
        this.inscriptionService = inscriptionService;
        this.inscriptionRepository = inscriptionRepository;
    }

    @PostMapping("/event/{eventId}/membres")
    public ResponseEntity<?> addMembreToEvenement(@RequestBody InscriptionDTO inscriptionDTO, @PathVariable String eventId) {
        Long evenementId = Long.parseLong(eventId);
        Long membreId = inscriptionDTO.getMembreId();

        // Vérifier si le membre existe
        if (!membreRepository.existsById(membreId) ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Les données fournies sont invalides");
        }

        // Vérifier si une inscription existe déjà pour ce membre et cet événement
        if (inscriptionRepository.existsByMembreIdAndEvenementId(membreId, evenementId)) {
            String successMessage = "{\"success\": true, \"message\": \"Le membre a été inscrit avec succès\"}";
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        }

        // Ajouter l'inscription seulement si elle n'existe pas déjà
        inscriptionService.addMembreToEvenement(membreId, evenementId);
        String successMessage = "{\"success\": true, \"message\": \"Le membre a été inscrit avec succès\"}";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }


    @GetMapping("/event/{eventId}/membres")
    public  ResponseEntity<?>  getMembresResponseByEvenementId(@PathVariable("eventId") String evenementIdString) {
        // Récupération des ID des membres associés à l'événement

        Long evenementId = Long.parseLong(evenementIdString);


            List<Inscription> inscriptions = inscriptionRepository.findByEvenementId(evenementId);

            // Création de la liste de membres à partir des inscriptions
            List<MembreDTO> membres = new ArrayList<>();
            for (Inscription inscription : inscriptions) {
                // Pour chaque inscription, récupérez les détails complets du membre
                MembreDTO membreDTO = membreService.getMembreParId(inscription.getMembreId());
                if (membreDTO != null) {
                    membres.add(membreDTO);
                }
            }

            // Convertir les membres en MembreResponseDTO
            List<MembreResponseDTO> responseDTOs = membres.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());

            // Retourner la liste des membres sous forme de ResponseEntity
            if(!responseDTOs.isEmpty())
            return ResponseEntity.ok(responseDTOs);
            else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),"Il n'y a pas de membres inscrit a l'evenement");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }


    }

    // Méthode pour convertir MembreDTO en MembreResponseDTO
    private MembreResponseDTO convertToResponseDTO(MembreDTO membreDTO) {
        MembreResponseDTO responseDTO = new MembreResponseDTO();
        responseDTO.setId(membreDTO.getId());
        responseDTO.setPrenom(membreDTO.getPrenom());
        responseDTO.setNom(membreDTO.getNom());

        // Autres champs à mapper si nécessaire
        return responseDTO;
    }
    @DeleteMapping ("/event/{eventId}/membres")
    public ResponseEntity<?> removeAllMembersFromEvenement(@PathVariable("eventId") String evenementIdString) {
        Long evenementId = Long.parseLong(evenementIdString);


        List<Inscription> inscriptions = inscriptionRepository.findByEvenementId(evenementId);

        for(Inscription ins : inscriptions) {

            inscriptionService.deleteInscription(ins.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/event/{eventId}/membres/{membreId}")
    public ResponseEntity<?> removeMemberFromEvent(@PathVariable("eventId") String evenementIdString,
                                                   @PathVariable("membreId") String membreIdString) {
        try {
        Long evenementId = Long.parseLong(evenementIdString);
        Long membreId = Long.parseLong(membreIdString);

        List<Inscription> inscriptions = inscriptionRepository.findByEvenementId(evenementId);
        Inscription inscriptionToGet = null;
        for (Inscription ins : inscriptions) {

            if (ins.getMembreId().equals(membreId))
                inscriptionToGet = ins;
        }

            if (inscriptionToGet !=null) {
                inscriptionService.deleteInscription(inscriptionToGet.getId());

                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }


}
