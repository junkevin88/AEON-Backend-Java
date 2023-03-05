package com.example.aeon.repository;

import com.example.aeon.model.Karyawan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface KaryawanRepository extends PagingAndSortingRepository<Karyawan, Long> {
    @Query("SELECT c from Karyawan c")// nama class
    Page<Karyawan> getAllData(Pageable pageable);

    @Query("SELECT c from Karyawan c WHERE c.id = :idkaryawan")
    Karyawan getByID(@Param("idkaryawan") Long idbebas);

    @Query("select c from Karyawan c where c.nama= :nama")
    public Page<Karyawan> findByNama(String nama , Pageable pageable);

    public Page<Karyawan> findByNamaLike(String nama , Pageable pageable);

    public Karyawan findByJk(String jk);

    public Karyawan findByAlamat(String alamat);

    public Karyawan findByStatus(String status);

    public Karyawan findByJkAndStatus(String jk, String status);

    @Query("select c from Karyawan c where  c.dob BETWEEN :dateMin and :dateMax")// nama class
    Page<Karyawan> getDataByPrice(Date dateMin, Date dateMax, Pageable pageable);

    @Query("select c from Karyawan c where  c.dob BETWEEN :dateMin and :dateMax and c.nama like :nama")// nama class
    Page<Karyawan> getDataByDobAndNama( Date dateMin, Date dateMax, String nama, Pageable pageable);


}
