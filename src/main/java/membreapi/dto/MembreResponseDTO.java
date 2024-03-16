package membreapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO représentant la réponse contenant les informations d'un membre.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembreResponseDTO {

    private String id; // Identifiant du membre

    private String nom; // Nom du membre

    private LocalDate dateNaissance; // Date de naissance du membre

    private String mail; // Adresse email du membre

    private String adresse; // Adresse du membre

    private String prenom; // Prénom du membre

    public MembreResponseDTO() {
    }
}
