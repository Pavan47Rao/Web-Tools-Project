/**
 * 
 */
package com.preciouspaws.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.preciouspaws.dao.ScheduleDAO;
import com.preciouspaws.exception.UserException;
import com.preciouspaws.response.Errors;
import com.preciouspaws.response.Message;

/**
 * @author pavanrao
 *
 */

@Controller
@RequestMapping("/api")
public class AvailableScheduleController {
	
	private static final Logger logger = Logger.getLogger(AppointmentController.class);

	@Autowired
	private ScheduleDAO scheduleDAO;

	@RequestMapping(value = "/schedule/add/doctorId/{doctorId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addSchedule(HttpServletRequest request, @PathVariable String doctorId)
			throws Exception {
		try {
			scheduleDAO.addSchedules(Integer.parseInt(doctorId));
			logger.info("Added schedules successfully");
			return new ResponseEntity<Object>("Added Schedules successfully", HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to add schedules",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/schedule/delete/schedule/{schedule}/userId/{userId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteSchedule(@PathVariable String schedule, @PathVariable String userId,
			HttpServletRequest request) throws Exception {
		try {
			scheduleDAO.deleteSchedule(schedule, Integer.parseInt(userId));
			logger.info("Deleted schedule successfully");
			return new ResponseEntity<Object>("Schedule deleted", HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("Failed to parse userid while deleting schedule",e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			logger.error("Failed to delete schedule",e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("Failed to delete", HttpStatus.BAD_REQUEST);
	}
}
