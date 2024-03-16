package membreapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO représentant les informations de connexion.
 */
@Getter
@Setter
public class LoginDTO {

    private String mail; // Adresse e-mail de l'utilisateur
    private String motDePasse; // Mot de passe de l'utilisateur
}
