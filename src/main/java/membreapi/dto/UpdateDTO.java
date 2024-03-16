package membreapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO utilisé pour mettre à jour les informations d'un membre.
 */
@Getter
@Setter
public class UpdateDTO {

    private String prenom; // Nouveau prénom du membre

    private String nom; // Nouveau nom du membre

    private LocalDate dateNaissance; // Nouvelle date de naissance du membre

    private String adresse; // Nouvelle adresse du membre

    private String motDePasse; // Nouveau mot de passe du membre

}
