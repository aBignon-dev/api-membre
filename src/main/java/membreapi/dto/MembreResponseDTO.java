package membreapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembreResponseDTO {
    private String id;
    private String nom;

    private LocalDate dateNaissance;

    private String mail ;

    private String adresse;

    public MembreResponseDTO() {

    }
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String prenom;


    // Autres attributs sans le mot de passe

    // Constructeurs, getters et setters
}
