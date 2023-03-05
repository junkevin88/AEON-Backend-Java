package com.example.aeon.service.impl;


import com.example.aeon.model.Karyawan;
import com.example.aeon.model.Rekening;
import com.example.aeon.repository.KaryawanRepository;
import com.example.aeon.repository.RekeningRepository;
import com.example.aeon.service.RekeningService;
import com.example.aeon.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class RekeningImpl implements RekeningService {

    public static final Logger log = LoggerFactory.getLogger(KaryawanImpl.class);

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public RekeningRepository rekeningRepository;

    @Autowired
    public KaryawanRepository karyawanRepository;


    @Override
    public Map insert(Rekening obj) {
        try {
            if ( templateResponse.checkNull(obj.getNama()) ) {
                return templateResponse.templateError("Nama Tidak boleh null");
            }

            if ( templateResponse.checkNull(obj.getNomor()) ) {
                return templateResponse.templateError("Nomor rekening Tidak boleh null");
            }
            Rekening saveObj = rekeningRepository.save(obj);
            return templateResponse.templateSukses(saveObj);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map update(Rekening obj) {
        try {

            if ( templateResponse.checkNull(obj.getId()) ) {
                return templateResponse.templateError("Id Rekening is required");
            }
            if ( templateResponse.checkNull(obj.getKaryawan()) ) {
                return templateResponse.templateError("Data Karyawan is required");
            }
            Rekening updateRekening = rekeningRepository.getbyID(obj.getId());
            if ( templateResponse.checkNull(updateRekening) ) {
                return templateResponse.templateError("Id Barang Not found");
            }

            updateRekening.setNama(obj.getNama());
            updateRekening.setJenis(obj.getJenis());
            updateRekening.setNomor(obj.getNomor());
            Date date = new Date(System.currentTimeMillis());
            updateRekening.setUpdated_date(date);

            Rekening updatedData = rekeningRepository.save(updateRekening);

            return templateResponse.templateSukses(updatedData);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map delete(Long obj) {
        try {
            if ( templateResponse.checkNull(obj) ) {
                return templateResponse.templateError("Id Rekening is required");
            }
            Rekening deleteRekening = rekeningRepository.getbyID(obj);
            if ( templateResponse.checkNull(deleteRekening) ) {
                return templateResponse.templateError("Id Rekening Not found");
            }

            deleteRekening.setDeleted_date(new Date());//
            rekeningRepository.save(deleteRekening);

            return templateResponse.templateSukses("sukses deleted");

        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map insert(Rekening rekening, Long idkaryawan) {
        try {
            if ( templateResponse.checkNull(rekening.getNama()) ) {
                return templateResponse.templateError("Nama is Requiered");
            }

            if ( templateResponse.checkNull(rekening.getNomor()) ) {
                return templateResponse.templateError("Nomor rekening is requiered");
            }

            if ( templateResponse.checkNull(idkaryawan) ) {
                return templateResponse.templateError("Id Karyawan is requiered");
            }
            Karyawan chekId = karyawanRepository.getByID(idkaryawan);
            if ( templateResponse.checkNull(chekId) ) {
                return templateResponse.templateError("Id Supplier NOt found");
            }
            //do save
            rekening.setKaryawan(chekId);
            Rekening rekeningSave = rekeningRepository.save(rekening);
            return templateResponse.templateSukses(rekeningSave);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map update(Rekening rekening, Long idkaryawan) {

        try {
/*
1. chek id supplirt - takutnya suppliar tidak ada
2. chek id barang - apakah ada atau tidak di db
3. simpan ke database
 */
            if ( templateResponse.checkNull(idkaryawan) ) {
                return templateResponse.templateError("Id Supplier is requiered");
            }
//            1. che id supplirt - =takutnyanya suppliar
            Karyawan chekId = karyawanRepository.getByID(idkaryawan);
            if ( templateResponse.checkNull(chekId) ) {
                return templateResponse.templateError("Id Supplier N0t found");
            }
            if ( templateResponse.checkNull(rekening.getId()) ) {
                return templateResponse.templateError("Id Barang is required");
            }
//            2. chek id barang - apakah ada atau ga
            Rekening checkRekening = rekeningRepository.getbyID(rekening.getId());
            if ( templateResponse.checkNull(checkRekening) ) {
                return templateResponse.templateError("Id Rekening Not found");
            }
//            3. simpan database : update
            checkRekening.setNama(rekening.getNama());
            checkRekening.setJenis(rekening.getJenis());
            checkRekening.setNomor(rekening.getNomor());
            checkRekening.setUpdated_date(new Date());
            Rekening saveUpdate = rekeningRepository.save(checkRekening);

            return templateResponse.templateSukses(saveUpdate);
        } catch ( Exception e ) {
            return templateResponse.templateError(e);
        }
    }


}
