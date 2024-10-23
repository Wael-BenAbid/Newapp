package iset.bizerte.elearning.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Cours extends AbstractEntity {

    private String titre;
    private String description;
    private boolean estouverte;
    private String urlcours;
    private Double prix;
    private String urlimage;

    // Relation ManyToMany avec les tags
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cours_tags",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag_> tags = new ArrayList<>();

    // Relation ManyToOne avec Matiere
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id")
    private Matiere matieres;

    // Relation ManyToMany avec les sections
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cours_sections",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id"))
    private List<Section> sections = new ArrayList<>();

    // Relation ManyToOne avec Enseignant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Enseignant teacher;

    // Relation ManyToOne avec Niveau
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;

    // Relation ManyToMany avec Etudiant
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cours")
    private List<Etudiant> etudiants = new ArrayList<>();

    // Relation ManyToMany avec Panier
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cours")
    private List<Panier> paniers = new ArrayList<>();

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + getId() +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", estouverte=" + estouverte +
                ", niveau=" + (niveau != null ? niveau.getId() : "null") +
                ", teacher=" + (teacher != null ? teacher.getId() : "null") +
                '}';
    }

}
