package membreapi.services;


import membreapi.models.Membre;
import membreapi.dto.MembreDTO;
import membreapi.mappers.MembreMapper;
import membreapi.repositories.MembreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreService {

    private final MembreRepository membreRepository;

    private final MembreMapper membreMapper;

    public MembreService(MembreRepository membreRepository, MembreMapper membreMapper) {
        this.membreRepository = membreRepository;
        this.membreMapper = membreMapper;
    }

    public MembreDTO createMembre(MembreDTO membreDTO) {
        Membre membre = membreMapper.toEntity(membreDTO);
        Membre savedMembre = membreRepository.save(membre); 
        return membreMapper.toDto(savedMembre);
    }

    public MembreDTO getMembreParId(Long membreId) {
        Membre existingMembre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable avec l'ID: " + membreId));
        return membreMapper.toDto(existingMembre);
    }

    public List<MembreDTO> getAllMembers() {
        List<Membre> membres = membreRepository.findAll();
        return membreMapper.toDtoList(membres);
    }


}
