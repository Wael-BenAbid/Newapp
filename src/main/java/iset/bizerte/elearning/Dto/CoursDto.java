package iset.bizerte.elearning.Dto;

import iset.bizerte.elearning.Entity.Cours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoursDto {
    private Long id;
    private String titre;
    private boolean estouverte;
    private String description;
    private String urlimage;
    private String urlcours;
    private Long idniveau;
    private Long idmatiere;
    private Long idenseignant;
    private List<Long> tagid;        // IDs des tags associés
    private List<Long> sectionid;    // IDs des sections associées
    private Double prix;

    // Propriétés ajoutées pour les relations
    private List<TagDto> tags;          // Liste des DTOs de Tag
    private List<SectionDto> sections;   // Liste des DTOs de Section
    private EnseignantDto enseignant;    // DTO pour Enseignant
    private NiveauDto niveau;            // DTO pour Niveau

    // Conversion de CoursDto à Cours
    public static Cours ToEntity(CoursDto coursDto) {
        if (coursDto == null) {
            return null;
        }
        return Cours.builder()
                .id(coursDto.getId())
                .titre(coursDto.getTitre())
                .description(coursDto.getDescription())
                .estouverte(coursDto.isEstouverte())
                .urlimage(coursDto.getUrlimage())
                .urlcours(coursDto.getUrlcours())
                .prix(coursDto.getPrix())
                .build();
    }

    public static CoursDto FromEntity(Cours cours) {
        if (cours == null) {
            return null;
        }
        return CoursDto.builder()
                .id(cours.getId())
                .titre(cours.getTitre())
                .description(cours.getDescription())
                .estouverte(cours.isEstouverte())
                .urlimage(cours.getUrlimage())
                .urlcours(cours.getUrlcours())
                .prix(cours.getPrix())
                .build();
    }
}
