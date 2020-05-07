/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */
@Entity
@Table(name = "Medication") 
public class Medication {

	@Id
	@Column(name="medicationid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int medicationId;
	
	@OneToOne
	@JoinColumn(name = "docappointmentid")
	private DoctorAppointment docAppointment;

	@Expose
	@Column(name = "treatingdoctor")
	private String treatingDoctor;
	
	@Expose
	@Column(name = "medicines")
	private String treatment;
	
	public int getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(int medicationId) {
		this.medicationId = medicationId;
	}

	public DoctorAppointment getDocAppointment() {
		return docAppointment;
	}

	public void setDocAppointment(DoctorAppointment docAppointment) {
		this.docAppointment = docAppointment;
	}

	public String getTreatingDoctor() {
		return treatingDoctor;
	}

	public void setTreatingDoctor(String treatingDoctor) {
		this.treatingDoctor = treatingDoctor;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public Medication() {
		// TODO Auto-generated constructor stub
	}

}
