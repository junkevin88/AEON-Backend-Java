package com.example.aeon.service.impl;

import com.example.aeon.dao.KaryawanTrainingRequest;
import com.example.aeon.model.Karyawan;
import com.example.aeon.model.KaryawanTraining;
import com.example.aeon.model.Training;
import com.example.aeon.repository.KaryawanRepository;
import com.example.aeon.repository.KaryawanTrainingRepository;
import com.example.aeon.repository.TrainingRepository;
import com.example.aeon.service.KaryawanTrainingService;
import com.example.aeon.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class KaryawanTrainingImpl implements KaryawanTrainingService {

    public static final Logger log = LoggerFactory.getLogger(KaryawanImpl.class);

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public TrainingRepository trainingRepository;

    @Autowired
    public KaryawanRepository karyawanRepository;

    @Autowired
    public KaryawanTrainingRepository karyawanTrainingRepository;

    @Override
    public Map insert(KaryawanTrainingRequest obj) {
        try {

            if ( templateResponse.checkNull(obj.getIdTraining()) ) {
                return templateResponse.templateError("Id Training Tidak boleh null");
            }
            if ( templateResponse.checkNull(obj.getIdKaryawan()) ) {
                return templateResponse.templateError("Id Karyawan Tidak boleh null");
            }
            Karyawan karyawanNew = karyawanRepository.getByID(obj.idKaryawan);
            if ( templateResponse.checkNull(karyawanNew) ) {
                return templateResponse.templateError("Id Karyawan Tidak ada di database");
            }

            Training trainingNew = trainingRepository.getbyID(obj.getIdTraining());
            if ( templateResponse.checkNull(trainingNew) ) {
                return templateResponse.templateError("Id Training Tidak ada di database");
            }

            //disimpan ke db: objek transaksi
            KaryawanTraining saveDate = new KaryawanTraining();
            saveDate.setTanggalTraining(obj.getTanggalTraining());
            saveDate.setKaryawan(karyawanNew);
            saveDate.setTraining(trainingNew);

            KaryawanTraining saveKryTraining = karyawanTrainingRepository.save(saveDate);
            return templateResponse.templateSukses(saveDate);

        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }
    }

    @Override
    public Map update(KaryawanTrainingRequest obj) {
        try {
            if ( templateResponse.checkNull(obj.getIdKaryawan()) ) {
                return templateResponse.templateError("Id Karyawan Tidak boleh null");
            }

            if ( templateResponse.checkNull(obj.getIdTraining()) ) {
                return templateResponse.templateError("Id Training Tidak boleh null");
            }
            if ( templateResponse.checkNull(obj.getId()) ) {
                return templateResponse.templateError("Id Karyawan Training Tidak boleh null");
            }

            Karyawan checkKry = karyawanRepository.getByID(obj.getIdKaryawan());
            if ( templateResponse.checkNull(checkKry) ) {
                return templateResponse.templateError("Id Karyawan Tidak ada di database");
            }

            Training checkTraining = trainingRepository.getbyID(obj.getIdTraining());
            if ( templateResponse.checkNull(checkTraining) ) {
                return templateResponse.templateError("Id Training Tidak ada di database");
            }

            KaryawanTraining checkKryTraining = karyawanTrainingRepository.getbyID(obj.getId());
            if ( templateResponse.checkNull(checkKryTraining) ) {
                return templateResponse.templateError("Id Karyawan Training Tidak ada di database");
            }
            //update disini
            checkKryTraining.setTraining(checkTraining);
            checkKryTraining.setKaryawan(checkKry);
            checkKryTraining.setTanggalTraining(checkKryTraining.getTanggalTraining());
            checkKryTraining.setUpdated_date(new Date());
            KaryawanTraining saveUpdate = karyawanTrainingRepository.save(checkKryTraining);
            return templateResponse.templateSukses(saveUpdate);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map delete(Long obj) {
        try {
            if ( templateResponse.checkNull(obj) ) {
                return templateResponse.templateError("Id Karyawan Training is required");
            }
            //            1. chek id barang
            KaryawanTraining checkKryTraining = karyawanTrainingRepository.getbyID(obj);
            if ( templateResponse.checkNull(checkKryTraining) ) {
                return templateResponse.templateError("Id Karyawan Training Not found");
            }

//            2. update , tanggal deleted saja
            checkKryTraining.setDeleted_date(new Date());//
            karyawanTrainingRepository.save(checkKryTraining);

            return templateResponse.templateSukses("sukses deleted");

        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }
    }
}

