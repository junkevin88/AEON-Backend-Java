package com.example.aeon.service.impl;


import com.example.aeon.model.DetailKaryawan;
import com.example.aeon.model.Karyawan;
import com.example.aeon.repository.DetailKaryawanRepository;
import com.example.aeon.repository.KaryawanRepository;
import com.example.aeon.service.KaryawanService;
import com.example.aeon.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
@Service
public class KaryawanImpl implements KaryawanService {

    @Autowired
    public KaryawanRepository karyawanRepository;

    @Autowired
    public DetailKaryawanRepository detailKaryawanRepository;

    public static final Logger log = LoggerFactory.getLogger(KaryawanImpl.class);

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map insertKryOnly(Karyawan karyawan) {

        try {
            if (templateResponse.checkNull(karyawan.getNama())) {
                return templateResponse.templateError("Nama is required");
            }
            Karyawan kryObj = karyawanRepository.save(karyawan);
            log.info("{}", "Sukses");
            return templateResponse.templateSukses(kryObj);
        } catch (Exception e) {
            log.info("{}", "Eror" + e);
            return templateResponse.templateError(e);
        }
    }

    @Override
    public Map insertKryAndDetail(Karyawan karyawan) {
        try {

//            1. validasi
            if (templateResponse.checkNull(karyawan.getNama())) {
                return templateResponse.templateError("Nama is Requiered");
            }
//            2. check detail
            if (templateResponse.checkNull(karyawan.getDetailKaryawan())) {
                return templateResponse.templateError("Karyawan's detail is Requiered");
            }
            if (templateResponse.checkNull(karyawan.getDetailKaryawan().getNik())) {
                return templateResponse.templateError("NIK is Requiered");
            }
            if (templateResponse.checkNull(karyawan.getRekenings())){
                
            }
            ;
//            3. insert karyawan

            Karyawan dataKaryawan = new Karyawan();
            dataKaryawan.setNama(karyawan.getNama());
            dataKaryawan.setJk(karyawan.getJk());
            dataKaryawan.setAlamat(karyawan.getAlamat());
            dataKaryawan.setDob(karyawan.getDob());
            dataKaryawan.setStatus(karyawan.getStatus());
            Karyawan karyawanNew = karyawanRepository.save(dataKaryawan);

//            4. insert karyawan detail : set FK dari karyawan
            DetailKaryawan dataDetailKry = new DetailKaryawan();
            dataDetailKry.setNik(karyawan.getDetailKaryawan().getNik());
            dataDetailKry.setNpwp(karyawan.getDetailKaryawan().getNpwp());
            dataDetailKry.setKaryawan(karyawanNew);
            detailKaryawanRepository.save(dataDetailKry);
            return templateResponse.templateSukses(karyawanNew);
        } catch (Exception e) {
            return templateResponse.templateError(e);
        }

    }


    @Override
    public Map update(Karyawan kryReq) {
        try {

            if (templateResponse.checkNull(kryReq.getId())) {
                return templateResponse.templateError("Id Barang is required");
            }
            Karyawan checkIdKry = karyawanRepository.getByID(kryReq.getId());
            if (templateResponse.checkNull(checkIdKry)) {
                return templateResponse.templateError("Id Barang Not found");
            }

            checkIdKry.setNama(kryReq.getNama());
            checkIdKry.setDob(kryReq.getDob());
            checkIdKry.setJk(kryReq.getJk());
            checkIdKry.setStatus(kryReq.getStatus());
            checkIdKry.setAlamat(kryReq.getAlamat());
            Date date = new Date(System.currentTimeMillis());
            checkIdKry.setUpdated_date(date);
            Karyawan dosave = karyawanRepository.save(checkIdKry);
            return templateResponse.templateSukses(dosave);
        } catch (Exception e) {
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map updateKryAndDetail(Karyawan karyawan) {
//        1. validasi
     try {
         if (templateResponse.checkNull(karyawan.getNama())) {
             return templateResponse.templateError("Nama is Requiered");
         }
//            2. check detail
         if (templateResponse.checkNull(karyawan.getDetailKaryawan())) {
             return templateResponse.templateError("Pembeli Detail is Requiered");
         }
         if (templateResponse.checkNull(karyawan.getDetailKaryawan().getNik())) {
             return templateResponse.templateError("Hp is Requiered");
         }
         //check id karyawan
         Karyawan updateKaryawan = karyawanRepository.getByID(karyawan.getId());
         if (templateResponse.checkNull(updateKaryawan)) {
             return templateResponse.templateError("Pembeli tidak ada di database");
         }
//            3. update karyawan
         updateKaryawan.setNama(karyawan.getNama());
         updateKaryawan.setJk(karyawan.getJk());
         updateKaryawan.setDob(karyawan.getDob());
         updateKaryawan.setAlamat(karyawan.getAlamat());
         updateKaryawan.setStatus(karyawan.getStatus());
         updateKaryawan.getDetailKaryawan().setNik(karyawan.getDetailKaryawan().getNik());
         updateKaryawan.getDetailKaryawan().setNpwp(karyawan.getDetailKaryawan().getNpwp());

//            4. do update ke database
         Karyawan updateKryAndDetail = karyawanRepository.save(updateKaryawan);
         return templateResponse.templateSukses(updateKryAndDetail);
     } catch( Exception e) {
         return templateResponse.templateError(e);
     }
}


    @Override
    public Map delete(Long karyawan) {
        try {
            if (templateResponse.checkNull(karyawan)) {
                return templateResponse.templateError("Id Karyawan is required");
            }

            Karyawan checkIdKaryawan = karyawanRepository.getByID(karyawan);
            if (templateResponse.checkNull(checkIdKaryawan)) {
                return templateResponse.templateError("Id Barang Not found");
            }

            checkIdKaryawan.setDeleted_date(new Date());//
            karyawanRepository.save(checkIdKaryawan);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateError(e);
        }
    }


    @Override
    public Map getAll(int size, int page) {
        try {
            Pageable show_data = PageRequest.of(page, size);
            Page<Karyawan> list = karyawanRepository.getAllData(show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {
            log.error("ada eror di method getAll:" + e);
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Map findByNama(String nama, Integer page, Integer size) {
        try {
            Pageable show_data = PageRequest.of(page, size);
            Page<Karyawan> list = karyawanRepository.findByNama(nama, show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {
            log.error("eror disini findByNama : " + e);
            //menampilkan responose
            return templateResponse.templateError(e);
        }

    }

    @Override
    public Page<Karyawan> findByNamaLike(String nama, Pageable pageable) {
        try {

            Page<Karyawan> list = karyawanRepository.findByNamaLike("%" + nama + "%", pageable);
//             public Page<Karyawan> findByNamaLike(String nama , Pageable pageable);
            return list;
        } catch (Exception e) {
            // manampilkan di terminal saja
            log.error("ada eror di method findByNamaLike:" + e);
            return null;
        }

    }
}
