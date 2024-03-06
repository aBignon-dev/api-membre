package membreapi.controllers;

import membreapi.*;
import membreapi.dto.MembreDTO;
import membreapi.dto.MembreResponseDTO;
import membreapi.dto.UpdateDTO;
import membreapi.models.Membre;
import membreapi.repositories.MembreRepository;
import membreapi.services.DeleteService;
import membreapi.services.MembreService;
import membreapi.services.UpdateService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;
    private final UpdateService updateService;
    private final DeleteService deleteService;

    private final MembreRepository membreRepository;


    public MembreController(MembreService membreService, UpdateService updateService, DeleteService deleteService, MembreRepository membreRepository) {
        this.membreService = membreService;
		this.updateService = updateService;
		this.deleteService = deleteService;
        this.membreRepository = membreRepository;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createMembre(@RequestBody MembreDTO membreDTO) {
        try {
            // Vérifier si le membre envoyé est complet
            if (membreDTO.getNom() == null || membreDTO.getPrenom() == null ||
                    membreDTO.getDateNaissance() == null || membreDTO.getAdresse() == null ||
                    membreDTO.getMail() == null || membreDTO.getMotDePasse() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Tous les champs du membre doivent être renseignés.");
            }

            // Vérifier si un membre avec cet email existe déjà
            if (membreRepository.existsByMail(membreDTO.getMail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Un membre avec cet email existe déjà.");
            }

            // Vérifier si un membre avec le même nom et prénom existe déjà
            if (membreRepository.existsByNomAndPrenom(membreDTO.getNom(), membreDTO.getPrenom())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Un membre avec ce nom et prénom existe déjà.");
            }

            MembreDTO createdMembre = membreService.createMembre(membreDTO);
            String successMessage = "{\"success\": true, \"message\": \"Membre créé\"}";

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdMembre.getId())
                    .toUri();

            return ResponseEntity
                    .created(location)
                    .body(successMessage);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Erreur création de membres");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    private ResponseEntity<?> validateMember(UpdateDTO memberUpdateDTO, String membreIdString) {
        // Récupérer l'ID du membre à partir de la chaîne
        Long membreId = Long.parseLong(membreIdString);

        // Récupérer le membre existant par son ID
        Membre existingMembre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable avec l'ID: " + membreId));

        // Vérifier si un autre membre avec le même nom et prénom existe déjà
        Membre otherMembreWithSameName = membreRepository.findByNomAndPrenomAndIdNot(memberUpdateDTO.getNom(), memberUpdateDTO.getPrenom(), membreId);

        if (otherMembreWithSameName != null) {
            // Si un membre avec le même nom et prénom existe déjà, renvoyer une ResponseEntity avec un message d'erreur approprié
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Un membre avec le même nom et prénom existe déjà.");
            return ResponseEntity.badRequest().body(errorResponse);
        }


        // Si toutes les validations sont réussies, retourner null pour indiquer que la validation a réussi
        return null;
    }

    @GetMapping
    public ResponseEntity<List<MembreResponseDTO>> getAllMembers() {
        List<MembreDTO> membres = membreService.getAllMembers();
        List<MembreResponseDTO> responseDTOs = membres.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{membreId}")
    public ResponseEntity<?> getMemberById(@PathVariable("membreId") String membreIdString) {
        try {
            Long membreId = Long.parseLong(membreIdString);
            MembreDTO membre = membreService.getMembreParId(membreId);
            if (membre != null) {
                MembreResponseDTO responseDTO = convertToResponseDTO(membre);
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            // Gérer les cas où l'ID du membre n'est pas un UUID valide
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "L'ID du membre n'est pas un id valide : "+membreIdString);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            // Gérer les cas où le membre n'est pas trouvé
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Membre introuvable avec l'ID: " + membreIdString);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Méthode pour convertir un MembreDTO en MembreResponseDTO
// Méthode pour convertir un MembreDTO en MembreResponseDTO
    public MembreResponseDTO convertToResponseDTO(MembreDTO membreDTO) {
        MembreResponseDTO responseDTO = new MembreResponseDTO();
        responseDTO.setId(membreDTO.getId());
        responseDTO.setNom(membreDTO.getNom());
        responseDTO.setPrenom(membreDTO.getPrenom());
        responseDTO.setDateNaissance(membreDTO.getDateNaissance());
        responseDTO.setMail(membreDTO.getMail());
        responseDTO.setAdresse(membreDTO.getAdresse());
        // Assurez-vous d'initialiser tous les autres champs requis de MembreResponseDTO
        return responseDTO;
    }

    //Gestion Update et Delete des membres 
    // Update 
    @PutMapping("/{membreId}")
    	public ResponseEntity<?> updateMember(@PathVariable("membreId") String membreIdString, @Valid @RequestBody UpdateDTO memberDTO) {
       
    	try {
            ResponseEntity<?> validationResponse = validateMember(memberDTO, membreIdString);
            if (validationResponse != null) {
                return validationResponse;
            }
            Long membreId = Long.parseLong(membreIdString);
            updateService.updateMembre(membreId, memberDTO);
            String successMessage = "{\"success\": true, \"message\": \"Membre mis à a jour\"}";
            return ResponseEntity.ok(successMessage);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                             HttpStatus.NOT_FOUND.getReasonPhrase(),
                                                             "Membre introuvable avec l'ID: " + membreIdString);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    
    // Delete un membre par son id
    @DeleteMapping("/{membreId}")
    public ResponseEntity<?> deleteMember(@PathVariable("membreId") String membreIdString) {
        try {
            // Convertir le string en Long
            Long membreId = Long.parseLong(membreIdString); 
            
            // Supprimer le membre à partir du service de suppression
            deleteService.deleteMembre(membreId);
            
            // Répondre avec un statut 204 No Content

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
                                                             HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                                                             "L'ID invalide : " + membreIdString);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            // Gérer les cas où le membre n'est pas trouvé
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), 
                                                             HttpStatus.NOT_FOUND.getReasonPhrase(), 
                                                             "Membre introuvable avec l'ID: " + membreIdString);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    

}
