/**
 * 
 */
package com.preciouspaws.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "Doctor") 
public class Doctor extends User implements Serializable{

	public Doctor() {
		// TODO Auto-generated constructor stub
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy = "doctor", orphanRemoval = true)
    private List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
	
	public List<DoctorAppointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<DoctorAppointment> appointments) {
		this.appointments = appointments;
	}

	@Expose
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="AvailableSchedules", joinColumns=@JoinColumn(name="docid"))
	@Column(name="availabletimings")
	private List<String> availableSchedules;

	public List<String> getAvailableSchedules() {
		return availableSchedules;
	}
	
	public void setAvailableSchedules(List<String> availableSchedules) {
		this.availableSchedules = availableSchedules;
	}
	
	public void addAppointment(DoctorAppointment docAppointment) {
		appointments.add(docAppointment);
		docAppointment.setDoctor(this);
	}
	
	public void updateDocAppointment(DoctorAppointment docApp) {
		for(DoctorAppointment app: appointments) {
			if(app.getAppointmentId() == docApp.getAppointmentId()) {
				app = docApp;
				break;
			}
		}
	}
	
	public void removeAppointment(DoctorAppointment docAppointment) {
		appointments.remove(docAppointment);
		docAppointment.setDoctor(null);
	}

	/**
	 * @param appointmentTime
	 */
	public void removeSchedule(String appointmentTime) {
		// TODO Auto-generated method stub
		availableSchedules.remove(appointmentTime);
	}
	
}
