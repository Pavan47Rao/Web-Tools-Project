/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */

@Entity(name = "Appointment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Appointment {

	@Id
	@Expose
	@Column(name="appointmentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int appointmentId;

	@Expose
	@Column(name = "animal")
	private String animal;
	
	@Expose
	@Column(name = "status")
	private String status;
	
	@Expose
	@Column(name = "appointmentTime")
	private String appointmentTime;
	
	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}
	
	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	/**
	 * 
	 */
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

}
