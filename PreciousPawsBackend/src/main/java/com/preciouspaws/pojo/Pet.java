/**
 * 
 */
package com.preciouspaws.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author pavanrao
 *
 */

@Entity
@Table(name = "Pet") 
public class Pet {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petid")
	@Expose
	private int petId;

    @Column(name = "animal")
    @Expose
	private String animal;

    @Column(name = "breed")
    @Expose
	private String breed;
    
    @Column(name = "age")
    @Expose
	private int age;
    
    @Column(name = "sex")
    @Expose
	private String sex;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="petownerid")
    private PetOwner petOwner;
    
    public PetOwner getPetOwner() {
		return petOwner;
	}

	public void setPetOwner(PetOwner petOwner) {
		this.petOwner = petOwner;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Pet() {
		// TODO Auto-generated constructor stub
	}

}
