/**
 * 
 */
package com.preciouspaws.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PetOwner") 
public class PetOwner extends User{

	@Expose
	@Column(name = "contactno")
	private String contactNo;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "petOwner", orphanRemoval = true)
    private List<Pet> pets = new ArrayList<Pet>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private List<DoctorAppointment> docAppointments = new ArrayList<DoctorAppointment>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "owner", orphanRemoval = true)
    private List<ServiceAppointment> serAppointments = new ArrayList<ServiceAppointment>();
	
	public List<ServiceAppointment> getSerAppointments() {
		return serAppointments;
	}

	public void setSerAppointments(List<ServiceAppointment> serAppointments) {
		this.serAppointments = serAppointments;
	}

	public List<DoctorAppointment> getDocAppointments() {
		return docAppointments;
	}

	public void setDocAppointments(List<DoctorAppointment> docAppointments) {
		this.docAppointments = docAppointments;
	}

	public List<DoctorAppointment> getAppointments() {
		return docAppointments;
	}

	public void setAppointments(List<DoctorAppointment> docAppointments) {
		this.docAppointments = docAppointments;
	}

	public String getContactNo() {
		return contactNo;
	}
	
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public void addPet(Pet pet) {
		pets.add(pet);
		pet.setPetOwner(this);
	}
	
	public void removePet(Pet pet) {
		pets.remove(pet);
		pet.setPetOwner(null);
	}
	
	public void addDocAppointment(DoctorAppointment docApp) {
		docAppointments.add(docApp);
		docApp.setOwner(this);
	}
	
	public void updateDocAppointment(DoctorAppointment docApp) {
		for(DoctorAppointment app: docAppointments) {
			if(app.getAppointmentId() == docApp.getAppointmentId()) {
				app = docApp;
				break;
			}
		}
	}
	
	public void updateSerAppointment(ServiceAppointment serApp) {
		for(ServiceAppointment app: serAppointments) {
			if(app.getAppointmentId() == serApp.getAppointmentId()) {
				app = serApp;
				break;
			}
		}
	}
	
	public void removeDocAppointment(DoctorAppointment docApp) {
		docAppointments.remove(docApp);
		docApp.setOwner(null);
	}

	public void addSerAppointment(ServiceAppointment serApp) {
		serAppointments.add(serApp);
		serApp.setOwner(this);
	}
	
	public void removeSerAppointment(ServiceAppointment serApp) {
		serAppointments.remove(serApp);
		serApp.setOwner(null);
	}
	
	public PetOwner() {
		// TODO Auto-generated constructor stub
	}

}
