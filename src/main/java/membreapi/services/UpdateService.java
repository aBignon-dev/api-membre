package membreapi.services;

import membreapi.dto.MembreDTO;
import membreapi.dto.UpdateDTO;
import membreapi.mappers.MembreMapper;
import membreapi.models.Membre;
import membreapi.repositories.MembreRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    private final MembreRepository membreRepository;
    private final MembreMapper membreMapper;




    public UpdateService(MembreRepository membreRepository, MembreMapper membreMapper) {
        this.membreRepository = membreRepository;
        this.membreMapper = membreMapper;
    }


    public MembreDTO updateMembre(Long membreId, UpdateDTO memberUpdateDTO) {
        Membre existingMembre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable avec l'ID: " + membreId));

        if (memberUpdateDTO.getPrenom() != null) {
            existingMembre.setPrenom(memberUpdateDTO.getPrenom());
        }
        if (memberUpdateDTO.getNom() != null) {
            existingMembre.setNom(memberUpdateDTO.getNom());
        }
        if (memberUpdateDTO.getDateNaissance() != null) {
            existingMembre.setDateNaissance(memberUpdateDTO.getDateNaissance());
        }
        if (memberUpdateDTO.getAdresse() != null) {
            existingMembre.setAdresse(memberUpdateDTO.getAdresse());
        }
        if (memberUpdateDTO.getMotDePasse() != null) {
            existingMembre.setMotDePasse(memberUpdateDTO.getMotDePasse());
        }


        existingMembre = membreRepository.save(existingMembre);
        return membreMapper.toDto(existingMembre);
    }
}
