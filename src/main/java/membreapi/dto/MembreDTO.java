package membreapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO représentant les informations d'un membre.
 */
@Getter
@Setter
public class MembreDTO {

    private String id; // Identifiant du membre

    @NotBlank(message = "Le prénom est requis")
    private String prenom; // Prénom du membre

    @NotBlank(message = "Le nom est requis")
    private String nom; // Nom du membre

    @NotNull(message = "La date de naissance est requise")
    private LocalDate dateNaissance; // Date de naissance du membre

    @NotBlank(message = "L'adresse est requise")
    private String adresse; // Adresse du membre

    @NotBlank(message = "L'adresse email est requise")
    @Email(message = "L'adresse email doit être valide")
    private String mail; // Adresse email du membre

    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse; // Mot de passe du membre
}
