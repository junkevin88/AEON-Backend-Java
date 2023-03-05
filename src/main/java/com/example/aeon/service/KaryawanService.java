package com.example.aeon.service;

import com.example.aeon.model.Karyawan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface KaryawanService {

    public Map insertKryOnly(Karyawan karyawan);

    public Map insertKryAndDetail(Karyawan karyawan);

    public Map update(Karyawan karyawan);

    public Map updateKryAndDetail(Karyawan karyawan);

    public Map delete(Long karyawan);

    public Map getAll(int size, int page);

    public Map findByNama(String nama, Integer page, Integer size);

    Page<Karyawan> findByNamaLike(String nama, Pageable pageable);


}

