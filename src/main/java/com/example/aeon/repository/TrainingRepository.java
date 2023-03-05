package com.example.aeon.repository;

import com.example.aeon.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends PagingAndSortingRepository<Training, Long> {

    @Query("select c from Training c WHERE c.id = :id")
    public Training getbyID(@Param("id") Long id);

    @Query("select c from Training c ")// nama class
    public Page<Training> getAllData(Pageable pageable);

    @Query("select c from Training c where c.tema like :tema")// nama class
    public Page<Training> findByTema(String tema , Pageable pageable);

    @Query("select c from Training c where c.namaPengajar like :namaPengajar")// nama class
    public Page<Training> findByNamaPengajar(String namaPengajar , Pageable pageable);
}

