package com.example.aeon.controller;

import com.example.aeon.model.Karyawan;
import com.example.aeon.model.Training;
import com.example.aeon.repository.TrainingRepository;
import com.example.aeon.service.TrainingService;
import com.example.aeon.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/training")
public class TrainingController {

    @Autowired
    public TrainingService trainingService;

    @Autowired
    public TrainingRepository trainingRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @PostMapping("")
    public ResponseEntity<Map> save(@RequestBody Training objModel) {
      Map map =  trainingService.insert(objModel);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map> update(@RequestBody Training objModel) {
        Map obj = trainingService.update(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = trainingService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByTema(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String tema) {
        Map map = new HashMap();
        Page<Training> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());//batasin roq

        if ( tema != null && !tema.isEmpty() ) {
            list = trainingRepository.findByTema("%" + tema + "%", show_data);
        } else {
            list = trainingRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getId(@PathVariable(value = "id") Long id) {
        Training obj1 = trainingRepository.getbyID(id);
        return new ResponseEntity<Map>(templateResponse.templateSukses(obj1), HttpStatus.OK);
    }


}
