/**
 * 
 */
package com.preciouspaws.dao;

import java.util.List;

import org.hibernate.query.Query;

import com.preciouspaws.pojo.Pet;

/**
 * @author pavanrao
 *
 */
public class PetDAO extends DAO{

	public Pet addPet(Pet pet) {
		begin();
		getSession().save(pet);
		commit();
		close();
		return pet;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public List<Pet> getPetByOwnerId(int id) {
		begin();
		Query query = getSession().createQuery("from Pet where petownerid=:id");
		query.setInteger("id", id);
		return (List<Pet>)query.getResultList();
	}
	
	public PetDAO() {
		// TODO Auto-generated constructor stub
	}

}
