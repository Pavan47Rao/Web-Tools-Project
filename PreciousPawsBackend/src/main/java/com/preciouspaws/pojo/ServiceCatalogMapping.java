/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pavanrao
 *
 */
@Entity
@Table(name = "ServiceCatalogMapping") 
public class ServiceCatalogMapping{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalogid")
	private int catalogId;
	
    @Column(name = "service")
	private String service;
    
    @Column(name = "price")
	private double price;
    
    @Column(name = "orgid")
	private int orgId;

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public ServiceCatalogMapping() {
		
	}
    
}
