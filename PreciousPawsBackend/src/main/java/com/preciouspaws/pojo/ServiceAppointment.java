/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */
@Entity(name="ServiceAppointment")
public class ServiceAppointment  extends Appointment{

	@Expose
	@Column(name = "service")
	private String service;
	
	@Expose
	@Column(name = "price")
	private Double price;

	
	@Expose
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ownerid")
	private PetOwner owner;
	
	public PetOwner getOwner() {
		return owner;
	}

	public void setOwner(PetOwner owner) {
		this.owner = owner;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 
	 */
	public ServiceAppointment() {
		// TODO Auto-generated constructor stub
	}

}
