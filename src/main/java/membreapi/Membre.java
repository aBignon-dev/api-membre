package membreapi;

import jakarta.persistence.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


@Entity
@Table(name = "Membre")
public class Membre {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilisez GenerationType.IDENTITY pour auto-incrémenter un Long
    private Long id;

	@Column(name = "nom", nullable = false)
	private String nom;

	@Column(name = "prenom", nullable = false)
	private String prenom;

	@Column(name = "dateNaissance", nullable = false)
	private LocalDate dateNaissance;

	@Column(name = "adresse", nullable = false)
	private String adresse;

	@Column(name = "mail", nullable = false, unique = true)
	private String mail;

	@Column(name = "motDePasse", nullable = false)
	private String motDePasse;

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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
	// vérification que l'object Membre existe bel et bien  
	 public boolean isPresent() {
	        return this.id != null; // Vérifie si l'ID du membre est non nul
	    }

	// Autres propriétés et méthodes
	// Hashage du mot de passe
	 public void setMotDePasse(String motDePasse) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(motDePasse.getBytes());
	            StringBuilder hexString = new StringBuilder();

	            for (byte b : hash) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }

	            this.motDePasse = hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	    }

	public String getMotDePasse() {
		return this.motDePasse;
	}
}
