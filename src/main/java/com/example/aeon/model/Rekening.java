package com.example.aeon.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "rekening")
@Where(clause = "deleted_date is null")
public class Rekening extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", length = 45)
    private String nama;

    @Column(name = "jenis", length = 10)
    private String jenis;

    @Column(name = "nomor", length = 10)
    private String nomor;

    @ManyToOne(targetEntity = Karyawan.class)
    private Karyawan karyawan;


}
