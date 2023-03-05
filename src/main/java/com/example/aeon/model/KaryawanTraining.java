package com.example.aeon.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "karyawan_training")
@Where(clause = "deleted_date is null")
public class KaryawanTraining extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tangal_training")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyMMdd")
    private Date tanggalTraining;


//    @ManyToMany(targetEntity = Karyawan.class, mappedBy = "karyawanTrainingList", cascade = CascadeType.ALL)
//    private List<Karyawan> karyawanList;

    @ManyToOne
    @JoinColumn(name = "training_id")
    Training training;

    @ManyToOne
    @JoinColumn(name = "karyawan_id")
    Karyawan karyawan;
}
