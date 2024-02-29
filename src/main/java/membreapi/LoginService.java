package membreapi;


import io.jsonwebtoken.security.InvalidKeyException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class LoginService {

    private final MembreRepository membreRepository;

    public LoginService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    // Injecter ici votre repository pour récupérer les informations de l'utilisateur

    public String authenticate(String mail, String password) {
        try {
            if (mail != null && password != null) {
                String validationStatus = validateCredentials(mail, password);
                if (validationStatus.equals("success")) {
                    // Générer un numéro aléatoire
                    Random random = new Random();
                    int randomNumber = random.nextInt(1000000); // Chiffre maximum arbitraire
                    return String.valueOf(randomNumber);
                } else {
                    return validationStatus; // Retourne le statut de validation en cas d'échec
                }
            } else {
                return "invalid_data"; // Données invalides
            }
        } catch (InvalidKeyException e) {
            return "unexpected_error"; // Erreur inattendue
        }
    }



    public String validateCredentials(String mail, String password) {
        if (mail != null && password != null) {
            Membre membre = membreRepository.findByMail(mail);
            if (membre != null) {
                String encryptedPassword = encryptPassword(password);
                if (membre.getMotDePasse().equals(encryptedPassword)) {
                    return "success"; // Succès de l'authentification
                } else {
                    return "wrong_password"; // Mot de passe incorrect
                }
            } else {
                return "user_not_found"; // Utilisateur non trouvé
            }
        } else {
            return "invalid_data"; // Données invalides
        }
    }


    private String encryptPassword(String password) {
        String retour;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

           retour = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            retour="";
        }
        return retour;
    }
}
