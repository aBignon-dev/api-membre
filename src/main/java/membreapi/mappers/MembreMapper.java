package membreapi.mappers;


import membreapi.models.Membre;
import membreapi.dto.MembreDTO;
import membreapi.services.MembreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring", uses= MembreService.class)
public interface MembreMapper {





    @Mapping(source = "prenom", target = "prenom")
    @Mapping(source = "nom", target = "nom")
    @Mapping(target = "id", ignore = true) // Ignorer l'ID pour qu'il soit auto-incrémenté par la base de données
    @Mapping(target = "dateNaissance", source = "dateNaissance")
    @Mapping(target = "adresse", source = "adresse")
    @Mapping(target = "mail", source = "mail")
    Membre toEntity(MembreDTO membreDTO);

    @Mapping(source = "prenom", target = "prenom")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "dateNaissance", target = "dateNaissance")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "mail", target = "mail")
    MembreDTO toDto(Membre membre);

    default List<MembreDTO> toDtoList(List<Membre> members) {
        return members.stream().map(this::toDto).collect(Collectors.toList());
    }

}
