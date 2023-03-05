package com.example.aeon.service.impl;

import com.example.aeon.model.Training;
import com.example.aeon.repository.TrainingRepository;
import com.example.aeon.service.TrainingService;
import com.example.aeon.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TrainingImpl implements TrainingService {

    @Autowired
    public TrainingRepository trainingRepository;

    public static final Logger log = LoggerFactory.getLogger(KaryawanImpl.class);

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map insert(Training obj) {
        try {
            if ( templateResponse.checkNull(obj.getTema()) ) {
                return templateResponse.templateError("Tema Tidak boleh null");
            }
            Training saveObj = trainingRepository.save(obj);
            return templateResponse.templateSukses(saveObj);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }
    }

    @Override
    public Map update(Training obj) {
        try {

            if ( templateResponse.checkNull(obj.getId()) ) {
                return templateResponse.templateError("Id Training is required");
            }
            if ( templateResponse.checkNull(obj.getTema()) ) {
                return templateResponse.templateError("Tema is required");
            }
            Training updateTraining = trainingRepository.getbyID(obj.getId());
            if ( templateResponse.checkNull(updateTraining) ) {
                return templateResponse.templateError("Id Barang Not found");
            }

            updateTraining.setTema(obj.getTema());
            updateTraining.setNamaPengajar(obj.getNamaPengajar());
            updateTraining.setUpdated_date(new Date());

            Training updatedData = trainingRepository.save(updateTraining);
            return templateResponse.templateSukses(updatedData);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }
    }

    @Override
    public Map delete(Long idTraining) {
        try {
            if ( templateResponse.checkNull(idTraining) ) {
                return templateResponse.templateError("Id Training is required");
            }
            Training deleteTraining = trainingRepository.getbyID(idTraining);
            if ( templateResponse.checkNull(deleteTraining) ) {
                return templateResponse.templateError("Id Training Not found");
            }

            deleteTraining.setDeleted_date(new Date());//
            trainingRepository.save(deleteTraining);

            return templateResponse.templateSukses("sukses deleted");

        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

}
