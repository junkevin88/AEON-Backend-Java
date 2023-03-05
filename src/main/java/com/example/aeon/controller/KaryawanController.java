package com.example.aeon.controller;

import com.example.aeon.model.Karyawan;
import com.example.aeon.repository.KaryawanRepository;
import com.example.aeon.service.KaryawanService;
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
@RequestMapping("/v1/karyawan")
public class KaryawanController {

    @Autowired
    KaryawanService karyawanService;

    @Autowired
    public KaryawanRepository karyawanRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @PostMapping("/add")
    public ResponseEntity<Map> save(@RequestBody Karyawan objModel) {
        Map map = new HashMap();
        Map obj = karyawanService.insertKryAndDetail(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map> update(@RequestBody Karyawan objModel) {
        Map obj = karyawanService.updateKryAndDetail(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = karyawanService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByBama(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama) {
        Map map = new HashMap();
        Page<Karyawan> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());//batasin roq

        if ( nama != null && !nama.isEmpty() ) {
            list = karyawanRepository.findByNama("%" + nama + "%", show_data);
        } else {
            list = karyawanRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getId(@PathVariable(value = "id") Long id) {
       Karyawan obj1 = karyawanRepository.getByID(id);
        return new ResponseEntity<Map>(templateResponse.templateSukses(obj1), HttpStatus.OK);
    }


}
