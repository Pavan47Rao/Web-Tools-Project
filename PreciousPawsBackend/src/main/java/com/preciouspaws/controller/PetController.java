/**
 * 
 */
package com.preciouspaws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.preciouspaws.dao.PetDAO;
import com.preciouspaws.dao.UserDAO;
import com.preciouspaws.pojo.Pet;
import com.preciouspaws.pojo.PetOwner;
/**
 * @author pavanrao
 *
 */

@Controller
@RequestMapping("/api")
public class PetController {

	@Autowired 
	PetDAO petDao;
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping(value="/pet/add/petOwner/{petOwnerId}", 
			method = RequestMethod.POST, 
			produces = "application/json")
	public ResponseEntity<Object> addPet(@RequestBody String body, HttpServletRequest request, @PathVariable int petOwnerId) throws Exception{
		
		JSONObject json = new JSONObject(body);
		Pet pet = new Pet();
		
		pet.setAge(json.getInt("age"));
		pet.setAnimal(json.getString("animal"));
		pet.setBreed(json.getString("breed"));
		pet.setSex(json.getString("sex"));
		
		PetOwner po = userDao.getPetOwnerById(petOwnerId);
		po.addPet(pet);
		Pet resp = petDao.addPet(pet);
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return new ResponseEntity<Object>(gson.toJson(resp).toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pets/petOwner/{petOwnerId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPetsByOwnerId(@PathVariable int petOwnerId) {
		List<Pet> pets = petDao.getPetByOwnerId(petOwnerId);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return new ResponseEntity<Object>(gson.toJson(pets), HttpStatus.OK);
	}
	
	public PetController() {
		// TODO Auto-generated constructor stub
	}

}
