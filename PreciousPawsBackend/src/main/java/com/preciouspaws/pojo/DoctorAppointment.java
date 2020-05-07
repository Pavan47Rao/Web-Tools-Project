/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */
@Entity(name="DoctorAppointment")
public class DoctorAppointment extends Appointment{
	
	
	@Expose
	@Column(name = "reason")
	private String reason;

	@Expose
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="doctorid")
	private Doctor doctor;
	
	@Expose
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ownerid")
	private PetOwner owner;
	
//	@Expose
//	@OneToOne(
//		mappedBy = "docAppointment",
//		cascade = CascadeType.ALL,
//		orphanRemoval = true,
//		fetch = FetchType.LAZY
//	)
//	private Medication medication;
//	
//	public Medication getMedication() {
//		return medication;
//	}
//
//	public void setMedication(Medication medication) {
//		this.medication = medication;
//	}

	public PetOwner getOwner() {
		return owner;
	}

	public void setOwner(PetOwner owner) {
		this.owner = owner;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

//	public void addMedication(Medication m) {
//		m.setDocAppointment(this);
//		this.medication = m;
//	}
	
	public DoctorAppointment() {
		// TODO Auto-generated constructor stub
	}

}
