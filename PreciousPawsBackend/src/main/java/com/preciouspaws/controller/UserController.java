/**
 * 
 */
package com.preciouspaws.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.preciouspaws.dao.ScheduleDAO;
import com.preciouspaws.dao.UserDAO;
import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.Doctor;
import com.preciouspaws.pojo.PetOwner;
import com.preciouspaws.pojo.User;
import com.preciouspaws.pojo.VeterinaryOrgUser;
import com.preciouspaws.response.Errors;
import com.preciouspaws.response.Message;
import com.preciouspaws.test.BusinessUtil;
import com.preciouspaws.validator.UserValidator;

/**
 * @author PavanRao
 *
 */

@Controller
@RequestMapping("/api")
public class UserController {

	private static final Logger logger = Logger.getLogger(AppointmentController.class);

	@Autowired
	UserDAO userDao;

	@Autowired
	ScheduleDAO scheduleDao;

	@Autowired
	UserValidator userValidator;

	@InitBinder("user")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	// check doctos's credentials
	@RequestMapping(value = "/doctor/{userId}/password/{password}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctor(@PathVariable String userId, @PathVariable String password,
			@RequestHeader HttpHeaders headers) throws Exception {
		try {

			JSONObject jsonToken = new JSONObject();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			df.setTimeZone(tz);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 20);
			Date date = calendar.getTime();
			jsonToken.put("expiration", df.format(date));
			String token = jsonToken.toString();
			logger.info("Created user token: " + token);
			
			Doctor user = userDao.authenticateDoctor(userId, password);
			JSONObject userJson = new JSONObject();
			userJson.put("userName", user.getUserName());
			userJson.put("fullName", user.getFullName());
			userJson.put("userId", user.getId());
			userJson.put("schedules", user.getAvailableSchedules());
			userJson.put("userToken", token);

			return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to check doctor's credentials", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// create a doctor
	@RequestMapping(value = "/doctor/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addDoctor(@RequestBody String body, HttpServletRequest request) throws Exception {

		// create token
		JSONObject jsonToken = new JSONObject();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 20);
		Date date = calendar.getTime();
		jsonToken.put("expiration", df.format(date));
		String token = jsonToken.toString();
		logger.info("Created user token: " + token);

		Gson gson = new Gson();
		Doctor d = gson.fromJson(body, Doctor.class);
		Doctor resp = userDao.createDoctor(d);
		scheduleDao.addSchedules(resp.getId());

		JSONObject userJson = new JSONObject();
		userJson.put("userName", resp.getUserName());
		userJson.put("fullName", resp.getFullName());
		userJson.put("userId", resp.getId());
		userJson.put("userToken", token);

		logger.info("Initiated email services");
		BusinessUtil.sendEmail(resp.getUserName(), "Greetings from Precious Paws",
				"Account created successfully. Thanks for joining us in treating the precious paws!");

		logger.info("Created doctor successfully");
		return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
	}

	// get doctor details by userid
	@RequestMapping(value = "/doctor/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctorById(@PathVariable String userId, @RequestHeader HttpHeaders headers)
			throws Exception {
		Doctor user = userDao.getDoctorById(Integer.parseInt(userId));
		JSONObject userJson = new JSONObject();
		userJson.put("userName", user.getUserName());
		userJson.put("fullName", user.getFullName());
		userJson.put("userId", user.getId());
		userJson.put("schedules", user.getAvailableSchedules());
		logger.info("Retrieved doctor successfully");
		return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
	}

	// get list of doctors
	@RequestMapping(value = "/doctors", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctors(@RequestHeader HttpHeaders headers) throws Exception {
		try {
			List<Doctor> doctors = userDao.getAllDoctors();
			List<JSONObject> listOfDoctors = new ArrayList<JSONObject>();
			for (Doctor doc : doctors) {
				JSONObject userJson = new JSONObject();
				userJson.put("userName", doc.getUserName());
				userJson.put("fullName", doc.getFullName());
				userJson.put("availableSchedule", doc.getAvailableSchedules());
				userJson.put("userId", doc.getId());
				listOfDoctors.add(userJson);
			}
			logger.info("Retrieved doctors successfully");
			return new ResponseEntity<Object>(listOfDoctors.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve doctors", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// check veterinary org's credentials
	@RequestMapping(value = "/vetOrg/{userId}/password/{password}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getVetOrg(@PathVariable String userId, @PathVariable String password,
			@RequestHeader HttpHeaders headers) throws Exception {
		try {
			
			// create token
			JSONObject jsonToken = new JSONObject();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			df.setTimeZone(tz);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 20);
			Date date = calendar.getTime();
			jsonToken.put("expiration", df.format(date));
			String token = jsonToken.toString();
			logger.info("Created user token: " + token);
			
			VeterinaryOrgUser user = userDao.getVetOrg(userId, password);
			JSONObject userJson = new JSONObject();
			userJson.put("userName", user.getUserName());
			userJson.put("fullName", user.getFullName());
			userJson.put("contactNo", user.getContactNo());
			userJson.put("address", user.getAddress());
			userJson.put("userId", user.getId());
			userJson.put("userToken", token);
			logger.info("Retrieved veterenary org user successfully");
			return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve vetreneary org user", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// get list of veterinary org users
	@RequestMapping(value = "/vetOrgUsers", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getVetOrgUsers(@RequestHeader HttpHeaders headers) throws Exception {
		try {
			List<VeterinaryOrgUser> vetOrgs = userDao.getAllVetOrgUsers();
			List<JSONObject> listOfVetOrgs = new ArrayList<JSONObject>();
			for (VeterinaryOrgUser doc : vetOrgs) {
				JSONObject userJson = new JSONObject();
				userJson.put("userName", doc.getUserName());
				userJson.put("fullName", doc.getFullName());
				userJson.put("address", doc.getAddress());
				userJson.put("contactNo", doc.getContactNo());
				userJson.put("services", doc.getServiceCatalogMap());
				userJson.put("userId", doc.getId());
				listOfVetOrgs.add(userJson);
			}
			logger.info("Retrieved veterenary org user ssuccessfully");
			return new ResponseEntity<Object>(listOfVetOrgs.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve veterenary org users", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// get veterinary org user by userId
	@RequestMapping(value = "/vetOrgUser/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getVetOrgUserById(@RequestHeader HttpHeaders headers, @PathVariable String userId)
			throws Exception {
		VeterinaryOrgUser vetOrg = userDao.getVetOrgById(Integer.parseInt(userId));
		JSONObject userJson = new JSONObject();
		userJson.put("userName", vetOrg.getUserName());
		userJson.put("fullName", vetOrg.getFullName());
		userJson.put("address", vetOrg.getAddress());
		userJson.put("contactNo", vetOrg.getContactNo());
		userJson.put("services", vetOrg.getServiceCatalogMap());
		userJson.put("userId", vetOrg.getId());
		logger.info("Retrieved veterenary org user by user id successfully");
		return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
	}

	// create a veterinary org user
	@RequestMapping(value = "/vetOrg/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addVetOrg(@RequestBody String body, HttpServletRequest request) throws Exception {
		// create token
		JSONObject jsonToken = new JSONObject();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 20);
		Date date = calendar.getTime();
		jsonToken.put("expiration", df.format(date));
		String token = jsonToken.toString();
		logger.info("Created user token: " + token);

		Gson gson = new Gson();
		VeterinaryOrgUser vetOrg = gson.fromJson(body, VeterinaryOrgUser.class);
		VeterinaryOrgUser resp = userDao.createVetOrgUser(vetOrg);

		JSONObject userJson = new JSONObject();
		userJson.put("userName", resp.getUserName());
		userJson.put("fullName", resp.getFullName());
		userJson.put("userId", resp.getId());
		userJson.put("userToken", token);

		BusinessUtil.sendEmail(resp.getUserName(), "Greetings from Precious Paws",
				"Account created successfully. Enjoy the Admin priveleages!");
		logger.info("Added veterenary org user successfully");
		return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
	}

	// check pet owner's credentials
	@RequestMapping(value = "/petOwner/{userId}/password/{password}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPetOwner(@PathVariable String userId, @PathVariable String password,
			@RequestHeader HttpHeaders headers) throws Exception {
		try {
			JSONObject jsonToken = new JSONObject();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			df.setTimeZone(tz);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 20);
			Date date = calendar.getTime();
			jsonToken.put("expiration", df.format(date));
			String token = jsonToken.toString();
			logger.info("Created user token: " + token);
			
			PetOwner user = userDao.getPetOwner(userId, password);
			JSONObject userJson = new JSONObject();
			userJson.put("userId", user.getId());
			userJson.put("userName", user.getUserName());
			userJson.put("fullName", user.getFullName());
			userJson.put("contactNo", user.getContactNo());
			userJson.put("userToken", token);
			logger.info("Verified veterenary org user successfully");
			return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to check pet owner's credentials", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// get all pet owners
	@RequestMapping(value = "/petOwners", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getPetOwners(@RequestHeader HttpHeaders headers) throws Exception {
		try {
			List<PetOwner> petOwners = userDao.getAllPetOwners();
			List<JSONObject> listOfPetOwners = new ArrayList<JSONObject>();
			for (PetOwner doc : petOwners) {
				JSONObject userJson = new JSONObject();
				userJson.put("userName", doc.getUserName());
				userJson.put("fullName", doc.getFullName());
				userJson.put("contactNo", doc.getContactNo());
				listOfPetOwners.add(userJson);
			}
			logger.info("Retrieved pet owners successfully");
			return new ResponseEntity<Object>(listOfPetOwners.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve pet owners", e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	// create a pet owner
	@RequestMapping(value = "/petOwner/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addPetOwner(@RequestBody String body, HttpServletRequest request) throws Exception {
		JSONObject jsonToken = new JSONObject();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 20);
		Date date = calendar.getTime();
		jsonToken.put("expiration", df.format(date));
		String token = jsonToken.toString();
		logger.info("Created user token: " + token);

		Gson gson = new Gson();
		PetOwner petOwner = gson.fromJson(body, PetOwner.class);
		PetOwner resp = userDao.createPetOwner(petOwner);

		JSONObject userJson = new JSONObject();
		userJson.put("userName", resp.getUserName());
		userJson.put("fullName", resp.getFullName());
		userJson.put("userId", resp.getId());
		userJson.put("userToken", token);
		BusinessUtil.sendEmail(resp.getUserName(), "Greetings from Precious Paws",
				"Account created successfully. Enjoy the services we provide!");
		logger.info("Added pet owner successfully");
		return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/auth/register", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> register(@RequestBody String body, BindingResult result) throws Exception {
		// Validting fields in form
		Gson gson = new Gson();
		Doctor inputDoctor = gson.fromJson(body, Doctor.class);
		userValidator.validate(inputDoctor, result);
		if (result.hasErrors()) {
			return new ResponseEntity<Object>("Invalid input", HttpStatus.BAD_REQUEST);
		}
		try {
			Doctor d = userDao.createDoctor((Doctor) inputDoctor);
			return new ResponseEntity<Object>(gson.toJson(d).toString(), HttpStatus.OK);
		} catch (UserException e) {
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

}
