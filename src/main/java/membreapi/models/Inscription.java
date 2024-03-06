package membreapi.models;

import jakarta.persistence.*;



@Entity
@Table(name = "Inscription")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "membre_id")
    private Long membreId;

    @Column(name = "evenement_id")
    private Long evenementId;

    // Constructeurs, getters et setters

    public Inscription() {
    }

    public Inscription(Long membreId, Long evenementId) {
        this.membreId = membreId;
        this.evenementId = evenementId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMembreId() {
        return membreId;
    }

    public void setMembreId(Long membreId) {
        this.membreId = membreId;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }
}
