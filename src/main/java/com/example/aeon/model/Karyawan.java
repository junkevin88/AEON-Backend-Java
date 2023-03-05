package com.example.aeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "karyawan")
@Where(clause = "deleted_date is null")
public class Karyawan extends AbstractDate implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nama", nullable = false, length = 45)
	private String nama;

	@Column(name = "jk", length = 10)
	private String jk;

	@Column(name = "dob")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyMMdd")
	private Date dob;

	@Column(name = "alamat", columnDefinition="TEXT")
	private String alamat;

	@Column(name = "status", length = 15)
	private String status;


	@OneToOne(mappedBy = "karyawan")
	private DetailKaryawan detailKaryawan;

	@JsonIgnore
	@OneToMany(mappedBy = "karyawan")
	List<KaryawanTraining> karyawanTrainings;

	@JsonIgnore
	@OneToMany(mappedBy = "karyawan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Rekening> rekenings;





}
