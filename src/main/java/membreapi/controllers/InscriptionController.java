package membreapi.controllers;

import membreapi.dto.MembreDTO;
import membreapi.repositories.MembreRepository;
import membreapi.services.MembreService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/membres")
public class InscriptionController {

    private final MembreService membreService;
    private final MembreRepository membreRepository; // Injection de MembreRepository

    public InscriptionController(MembreService membreService, MembreRepository membreRepository) {
        this.membreService = membreService;
        this.membreRepository = membreRepository; // Initialisation de MembreRepository
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createMembre(@RequestBody MembreDTO membreDTO) {
        try {
            // Vérifier si un membre avec cet email existe déjà
            if (membreRepository.existsByMail(membreDTO.getMail())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Un membre avec cet email existe déjà.");
            }

            // Vérifier si un membre avec le même nom et prénom existe déjà
            if (membreRepository.existsByNomAndPrenom(membreDTO.getNom(), membreDTO.getPrenom())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Un membre avec ce nom et prénom existe déjà.");
            }

            // Vérifier si le mot de passe contient au moins 6 caractères
            if (membreDTO.getMotDePasse().length() < 6) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Le mot de passe doit contenir au moins 6 caractères.");
            }

            // Vérifier si la date de naissance donne un âge supérieur à 18 ans
            LocalDate today = LocalDate.now();
            LocalDate dateNaissance = membreDTO.getDateNaissance();
            Period age = Period.between(dateNaissance, today);
            if (age.getYears() < 18) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("La date de naissance donne un âge inférieur à 18 ans.");
            }

            MembreDTO createdMembre = membreService.createMembre(membreDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdMembre.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdMembre);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Un membre avec cet email existe déjà.");
        }
    }


}
