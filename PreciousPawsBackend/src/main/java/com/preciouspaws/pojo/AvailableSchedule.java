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
@Table(name = "AvailableSchedules") 
public class AvailableSchedule {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
	private int sId;
	
    @Column(name = "availabletimings")
	private String availableTimings;
    
    @Column(name="docid")
    private int docId;
	
	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getAvailableTimings() {
		return availableTimings;
	}

	public void setAvailableTimings(String availableTimings) {
		this.availableTimings = availableTimings;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public AvailableSchedule() {
		// TODO Auto-generated constructor stub
	}

}
