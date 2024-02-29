package membreapi;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MembreDTO {
	private String id;

	@NotBlank(message = "Le prénom est requis")
	private String prenom;

	@NotBlank(message = "Le nom est requis")
	private String nom;

	@NotNull(message = "La date de naissance est requise")
	private LocalDate dateNaissance;

	@NotBlank(message = "L'adresse est requise")

	@NotBlank(message = "L'adresse email est requise")
	@Email(message = "L'adresse email doit être valide")
	private String mail;

	@NotBlank(message = "Le mot de passe est requis")
	@Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
	private String motDePasse;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String adresse;

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	// Autres getters et setters si nécessaire
}
