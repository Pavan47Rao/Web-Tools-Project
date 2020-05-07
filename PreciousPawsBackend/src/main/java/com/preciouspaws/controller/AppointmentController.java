/**
 * 
 */
package com.preciouspaws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
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
import com.preciouspaws.dao.AppointmentDAO;
import com.preciouspaws.dao.ScheduleDAO;
import com.preciouspaws.dao.UserDAO;
import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.Doctor;
import com.preciouspaws.pojo.DoctorAppointment;
import com.preciouspaws.pojo.Medication;
import com.preciouspaws.pojo.PetOwner;
import com.preciouspaws.pojo.ServiceAppointment;
import com.preciouspaws.response.Errors;
import com.preciouspaws.response.Message;
import com.preciouspaws.test.BusinessUtil;

/**
 * @author pavanrao
 *
 */
@Controller
@RequestMapping("/api")
public class AppointmentController {
	
	private static final Logger logger = Logger.getLogger(AppointmentController.class);

	@Autowired
	AppointmentDAO appointmentDao;

	@Autowired
	UserDAO userDao;

	@Autowired
	ScheduleDAO scheduleDao;

	@RequestMapping(value = "/serviceAppointment/add/owner/{ownerId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addServiceAppointment(@RequestBody String body, HttpServletRequest request,
			@PathVariable int ownerId) throws Exception {
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			ServiceAppointment sa = gson.fromJson(body, ServiceAppointment.class);
			PetOwner po = userDao.getPetOwnerById(ownerId);
			po.addSerAppointment(sa);
			ServiceAppointment resp = appointmentDao.createServiceAppointment(sa);
			logger.info("Initiated mailing service");
			BusinessUtil.sendEmail(po.getUserName(), "Appointment created", "Created Service Appointment successfully");
			logger.info("Created Service Appointment successfully");
			return new ResponseEntity<Object>(gson.toJson(resp).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to create service appointment",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doctorAppointment/add/doctor/{doctorId}/owner/{ownerId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addDoctorAppointment(@RequestBody String body, HttpServletRequest request,
			@PathVariable int doctorId, @PathVariable int ownerId) throws Exception {
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			DoctorAppointment da = gson.fromJson(body, DoctorAppointment.class);
			Doctor doc = userDao.getDoctorById(doctorId);
			doc.addAppointment(da);
			PetOwner po = userDao.getPetOwnerById(ownerId);
			po.addDocAppointment(da);
			DoctorAppointment resp = appointmentDao.createDoctorAppointment(da);
			logger.info("Initiated mailing service");
			BusinessUtil.sendEmail(po.getUserName(), "Appointmenet created", "Created Doctor Appointment successfully");
			logger.info("Created Doctor Appointment successfully");
			return new ResponseEntity<Object>(gson.toJson(resp).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to create doctor appointment",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doctorAppointment/update/doctor/{doctorId}/owner/{ownerId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> updateDoctorAppointment(@RequestBody String body, HttpServletRequest request,
			@PathVariable int doctorId, @PathVariable int ownerId) throws Exception {
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			DoctorAppointment da = gson.fromJson(body, DoctorAppointment.class);
			Doctor doc = userDao.getDoctorById(doctorId);
			doc.updateDocAppointment(da);
			PetOwner po = userDao.getPetOwnerById(ownerId);
			po.updateDocAppointment(da);
			appointmentDao.updateDoctorAppointment(da);
			if (da.getStatus().equals("Confirmed")) {
				scheduleDao.deleteSchedule(da.getAppointmentTime(), doctorId);
				doc.removeSchedule(da.getAppointmentTime());
			}
			BusinessUtil.sendEmail(po.getUserName(), "Appointmenet updated",
					"Your Doctor Appointment has been updated. Please Login for more info");
			logger.info("Updated doctor Appointment successfully");
			return new ResponseEntity<Object>("Updated appointment successfully", HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to update doctor appointment",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/medication/add/doctorAppointment/{doctorAppointmentId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addMedication(@RequestBody String body, HttpServletRequest request,
			@PathVariable int doctorAppointmentId) throws Exception {
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			Medication m = gson.fromJson(body, Medication.class);
			Medication resp = appointmentDao.createMedication(m, doctorAppointmentId);
			logger.info("Added Medication successfully");
			return new ResponseEntity<Object>(gson.toJson(resp).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to add medication",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/medication/update/appointmentId/{appointmentId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> updateMedication(@RequestBody String body, HttpServletRequest request, @PathVariable String appointmentId)
			throws Exception {
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			Medication m = gson.fromJson(body, Medication.class);
			appointmentDao.updateMedication(m, Integer.parseInt(appointmentId));
			logger.info("Updated medication successfully");
			return new ResponseEntity<Object>("Updated Medication", HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to create update medication",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doctorAppointment/{doctorAppointmentId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctorAppointmentById(HttpServletRequest request,
			@PathVariable int doctorAppointmentId) throws Exception {
		try {
			DoctorAppointment appointment = appointmentDao.getDoctorAppointmentById(doctorAppointmentId);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Doctor Appointment by appointment id successfully");
			return new ResponseEntity<Object>(gson.toJson(appointment).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve doctor appointment by appointment Id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doctorAppointments/{doctorId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctorAppointmentByDoctorId(@PathVariable int doctorId) throws UserException {
		try {
			List<DoctorAppointment> docAppointments = appointmentDao.getDoctorAppointmentsByDoctorId(doctorId);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Doctor Appointments by doctor id successfully");
			return new ResponseEntity<Object>(gson.toJson(docAppointments).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve doctor appointment by doctor id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/serviceAppointments", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getServiceAppointments() throws UserException {
		try {
			List<ServiceAppointment> serAppointments = appointmentDao.getServiceAppointments();
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Service Appointments successfully");
			return new ResponseEntity<Object>(gson.toJson(serAppointments).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve service appointments",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/serviceAppointment/{sId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getServiceAppointmentById(@PathVariable String sId) throws UserException {
		try {
			ServiceAppointment serAppointment = appointmentDao.getServiceAppointmentById(Integer.parseInt(sId));
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Service Appointment successfully by appointment id");
			return new ResponseEntity<Object>(gson.toJson(serAppointment).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve service appointment by appointment id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/serviceAppointments/owner/{oId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getServiceAppointmentsByOwnerId(@PathVariable String oId) throws UserException {
		try {
			List<ServiceAppointment> serAppointment = appointmentDao
					.getServiceAppointmentsByOwnerId(Integer.parseInt(oId));
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Service Appointments by owner id successfully");
			return new ResponseEntity<Object>(gson.toJson(serAppointment).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve service appointment by owner id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doctorAppointments/owner/{oId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getDoctorAppointmentsByOwnerId(@PathVariable String oId) throws UserException {
		try {
			List<DoctorAppointment> doctorAppointment = appointmentDao
					.getDoctorAppointmentsByOwnerId(Integer.parseInt(oId));
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			logger.info("Retrieved Doctor Appointments by owner id successfully");
			return new ResponseEntity<Object>(gson.toJson(doctorAppointment).toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve doctor appointment by owner id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/medication/appointmentId/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getMedicationByAppointmentId(@PathVariable String id) throws UserException {
		try {
			Medication medication = appointmentDao.getMedicationByAppointmentId(Integer.parseInt(id));

			logger.info("Retrieved Medication by appointment id successfully");
			JSONObject userJson = new JSONObject();
			userJson.put("medicationId", medication.getMedicationId());
			userJson.put("treatingDoctor", medication.getTreatingDoctor());
			userJson.put("treatment", medication.getTreatment());
			return new ResponseEntity<Object>(userJson.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to retrieve Medication by appointment id",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update/serviceAppointment/sId/{sId}/status/{status}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> updateServiceAppointment(@PathVariable String sId, @PathVariable String status)
			throws UserException {
		try {
			appointmentDao.updateServiceAppointment(Integer.parseInt(sId), status);
			logger.info("Updated Service Appointment successfully");
			return new ResponseEntity<Object>("Updated Service Appointment successfully", HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to update service appointment",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	public AppointmentController() {
		// TODO Auto-generated constructor stub
	}

}
