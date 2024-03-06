package membreapi.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import membreapi.ErrorResponse;
import membreapi.dto.LoginDTO;
import membreapi.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/membres")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest, HttpServletResponse response) {
        if (loginRequest.getMail() != null && loginRequest.getMotDePasse() != null
                && !loginRequest.getMail().isEmpty() && !loginRequest.getMotDePasse().isEmpty()) {
            String validationStatus = loginService.validateCredentials(loginRequest.getMail(), loginRequest.getMotDePasse());
            switch (validationStatus) {
                case "success":
                    // Authentification réussie, générer le JWT et renvoyer une réponse réussie
                    String jwt = loginService.authenticate(loginRequest.getMail(), loginRequest.getMotDePasse());
                    // Ajouter le JWT dans un cookie
                    Cookie cookie = new Cookie("JWT", jwt);
                    cookie.setPath("/");
                    cookie.setMaxAge(3600); // Durée de validité du cookie en secondes (par exemple, 1 heure)
                    response.addCookie(cookie);
                    return ResponseEntity.ok(cookie);
                case "wrong_password":
                    // Mot de passe incorrect, renvoyer une réponse avec une erreur 401
                    ErrorResponse wrongPasswordResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            "Mot de passe incorrect");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(wrongPasswordResponse);
                case "user_not_found":
                    // Utilisateur non trouvé, renvoyer une réponse avec une erreur 401
                    ErrorResponse userNotFoundResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            "Utilisateur non trouvé");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userNotFoundResponse);
                case "invalid_data":
                    // Données invalides, renvoyer une réponse avec une erreur 400
                    ErrorResponse invalidDataResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Données invalides");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDataResponse);
                case "unexpected_error" :
                    // Autre cas, renvoyer une réponse avec une erreur 500
                    ErrorResponse defaultResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            "Une erreur inattendue s'est produite");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(defaultResponse);
                default:
                    ErrorResponse defaultDataResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Données entrées invalides");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultDataResponse);
            }
        } else {
            ErrorResponse invalidDataResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Données entrées invalides");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDataResponse);
        }

    }

}

