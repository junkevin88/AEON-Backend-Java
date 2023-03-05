package com.example.aeon.repository;

import com.example.aeon.model.DetailKaryawan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailKaryawanRepository extends PagingAndSortingRepository<DetailKaryawan, Long> {

    @Query("select c from DetailKaryawan c WHERE c.id = :id")
    public DetailKaryawan getbyID(@Param("id") Long id);


}
