package iset.bizerte.elearning.Service;

import iset.bizerte.elearning.Dto.CoursDto;
import iset.bizerte.elearning.Dto.MatiereDto;
import iset.bizerte.elearning.Dto.NiveauDto;

import java.util.Date;
import java.util.List;

public interface CoursService {

    List<CoursDto> findAll();
    CoursDto findById(Long id);
    CoursDto  save(CoursDto request);
    void deleteById(Long id);
    List<CoursDto > findbyobjet(String kye);
    List<CoursDto> findDate(Date start, Date end);
    CoursDto uppdate(CoursDto request);
    Void addcoursestostudent( Long id);
}
