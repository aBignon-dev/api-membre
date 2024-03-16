package membreapi.dto;

import java.time.LocalDate;

public class UpdateDTO {
	
	    private String prenom;
	    private String nom;
	    private LocalDate dateNaissance;
	    private String adresse;
		private String motDePasse;

	    // Getters et setters
	    public String getPrenom() {
			return prenom;
		}


		public String getNom() {
			return nom;
		}

		public LocalDate getDateNaissance() {
			return dateNaissance;
		}

		public String getAdresse() {
			return adresse;
		}

		public String getMotDePasse() {
			return motDePasse;
		}




}
