package iset.bizerte.elearning.Service.IMPL;

import iset.bizerte.elearning.Dto.CoursDto;
import iset.bizerte.elearning.Dto.NiveauDto;
import iset.bizerte.elearning.Dto.PanierDto;
import iset.bizerte.elearning.Entity.*;
import iset.bizerte.elearning.Repository.*;
import iset.bizerte.elearning.Service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoursServiceImpl implements CoursService {


private final CoursRepository coursRepository;
    private final NiveauRepository niveauRepository;
    private final MatiereRepository matiereRepository;
    private final EnseignantRepository enseignantRepository;
    private final TagRepository tagRepository;
    private final SectionRepository sectionRepository;
    private final EtudiantRepository etudiantRepository;
    private final PanierRepository panierRepository;
    @Override
    public List<CoursDto> findAll() {
        return coursRepository.findAll().stream()
                .map(CoursDto::FromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public CoursDto findById(Long id) {
        Optional<Cours> cours = coursRepository.findById(id);
        if (cours.isPresent()) {
            return CoursDto.FromEntity(cours.get());
        }
        else {
            throw new RuntimeException("Cours not found");
        }
    }

    @Override
    public CoursDto save(CoursDto request) {
        Cours cours = CoursDto.ToEntity(request);
        Optional<Matiere> matierefound = matiereRepository.findById(request.getIdmatiere());
        Optional<Niveau> niveaufound = niveauRepository.findById(request.getIdniveau());
        Optional<Enseignant> Enseignantfound = enseignantRepository.findById(request.getIdenseignant());

        if (matierefound.isPresent() && niveaufound.isPresent() && Enseignantfound.isPresent()) {
            cours.setMatieres(matierefound.get());
            cours.setNiveau(niveaufound.get());
            cours.setTeacher(Enseignantfound.get());

            List<Tag_> tagtoadd;
            if (request.getTagid().isEmpty()) {
                throw new IllegalArgumentException("you need to add a tag ");
            } else {
                tagtoadd = new ArrayList<>();
                for (Long Idmatieres : request.getTagid()) {
                    Optional<Tag_> tagfound = tagRepository.findById(Idmatieres);
                    tagfound.ifPresent(tagtoadd::add);
                }

            }
           /* List<Section> sectiontoadd;
            if (request.getSectionid().isEmpty()) {
                throw new IllegalArgumentException("you need to add a section ");
            } else {
                sectiontoadd = new ArrayList<>();
                for (Long Idsection : request.getSectionid()) {
                    Optional<Section> sectionfound = sectionRepository.findById(Idsection);
                    sectionfound.ifPresent(sectiontoadd::add);
                }
            }*/
            System.err.println(tagtoadd);
            cours.setTags(tagtoadd);
            //cours.setSections(sectiontoadd);
            Cours coursSaved = coursRepository.save(cours);
            return CoursDto.FromEntity(coursSaved);


        }
        else {
            throw new RuntimeException("Cours not found");
        }


    }

    @Override
    public void deleteById(Long id) {
          Optional<Cours> cours = coursRepository.findById(id);
          if (cours.isPresent()) {
                coursRepository.deleteById(id);
            }
            else {
                throw new RuntimeException("Cours not found");
            }
    }

    @Override
    public List<CoursDto> findbyobjet(String kye) {
        return coursRepository.searchByObjetStartsWith(kye)
                .stream()
                .map(CoursDto::FromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoursDto> findDate(Date start, Date end) {
        return List.of();
    }

    @Override
    public CoursDto uppdate(CoursDto request) {
        Optional<Cours> cours = coursRepository.findById(request.getId());
        if (cours.isPresent()) {
            Cours coursconverted = CoursDto.ToEntity(request);
            cours.get().setMatieres(coursconverted.getMatieres());
            cours.get().setTeacher(coursconverted.getTeacher());
            cours.get().setNiveau(coursconverted.getNiveau());
            cours.get().setTags(coursconverted.getTags());
            cours.get().setSections(coursconverted.getSections());
            Cours coursSaved = coursRepository.save(cours.get());
            return CoursDto.FromEntity(coursSaved);
        }
        else {
            throw new RuntimeException("Cours not found");
        }
    }

    public Void addcoursestostudent(Long idpanier) {
        Optional<Panier> panierOptional = panierRepository.findById(idpanier);
        if (panierOptional.isPresent()) {
            Panier panier = panierOptional.get();
            printCoursType(panier);

            Optional<Etudiant> etudiantOptional = etudiantRepository.findById(panier.getEtudiant().getId());

            if (!panier.getCours().isEmpty() && etudiantOptional.isPresent()) {
                Etudiant etudiant = etudiantOptional.get();
                List<Cours> coursExistants = etudiant.getCours() != null ? etudiant.getCours() : new ArrayList<>();

                // Ajout des cours à l'étudiant
                for (Cours cours : panier.getCours()) {
                    System.out.println("Ajout du cours : " + cours);
                    if (!coursExistants.contains(cours)) {
                        coursExistants.add(cours);
                    }
                }

                etudiant.setCours(coursExistants);
                etudiantRepository.save(etudiant);
                return null;
            } else {
                throw new IllegalArgumentException("Panier vide ou étudiant non trouvé.");
            }
        }
        throw new IllegalArgumentException("Panier non trouvé.");
    }
    public static void printCoursType(Panier panier) {
        for (Object cours : panier.getCours()) {
            if (cours instanceof Cours) {
                System.out.println("Type de cours : " + cours.getClass().getSimpleName());
            } else {
                System.out.println("Type inattendu : " + cours.getClass().getSimpleName());
            }
        }
    }

}

