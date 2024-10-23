package iset.bizerte.elearning.contoller;

import iset.bizerte.elearning.Dto.CoursDto;
import iset.bizerte.elearning.Service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursService coursService;


@GetMapping("/addcoursestostudent/{id}")
    public Void addcoursestostudent(@PathVariable("id")  Long id) {
        return coursService.addcoursestostudent(id);
    }

    @GetMapping("/listall")
    public List<CoursDto> findAll() {
        return coursService.findAll();
    }

    @GetMapping("/getbyid/{id}")
    public CoursDto findById(@PathVariable("id") Long id) {
        return coursService.findById(id);
    }

    @PostMapping("/save")
    public CoursDto save(@RequestBody CoursDto request) {
        return coursService.save(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        coursService.deleteById(id);
    }

    @GetMapping("/search/{kye}")
    public List<CoursDto> findbyobjet(@PathVariable("kye") String kye) {
        return coursService.findbyobjet(kye);
    }

    @GetMapping("/finddate")
    public List<CoursDto> findDate( Date start, Date end) {
        return coursService.findDate(start, end);
    }

    @PutMapping("/update")
    public CoursDto update(@RequestBody CoursDto request) {
        return coursService.uppdate(request);
    }
}
