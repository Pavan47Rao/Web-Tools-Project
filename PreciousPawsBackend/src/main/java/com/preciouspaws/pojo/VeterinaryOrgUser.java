/**
 * 
 */
package com.preciouspaws.pojo;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

/**
 * @author pavanrao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VeterinaryOrgUser") 
public class VeterinaryOrgUser extends User {

	@Column(name = "contactno")
	private String contactNo;
	
	@Column(name = "address")
	private String address;
	
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    @ElementCollection
    @CollectionTable(name = "ServiceCatalogMapping", 
      joinColumns = {@JoinColumn(name = "orgid", referencedColumnName = "id")})
    @MapKeyColumn(name = "service")
    @Column(name = "price")
    private Map<String, Double> serviceCatalogMap;
    
	public Map<String, Double> getServiceCatalogMap() {
		return serviceCatalogMap;
	}

	public void setServiceCatalogMap(Map<String, Double> serviceCatalogMap) {
		this.serviceCatalogMap = serviceCatalogMap;
	}

	public VeterinaryOrgUser() {
		// TODO Auto-generated constructor stub
	}

}
