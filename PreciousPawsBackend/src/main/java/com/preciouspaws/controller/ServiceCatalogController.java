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
import com.preciouspaws.dao.ServiceCatalogDAO;
import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.ServiceCatalogMapping;
import com.preciouspaws.response.Errors;
import com.preciouspaws.response.Message;

/**
 * @author pavanrao
 *
 */
@Controller
@RequestMapping("/api")
public class ServiceCatalogController {
	
	private static final Logger logger = Logger.getLogger(AppointmentController.class);

	@Autowired
	private ServiceCatalogDAO serviceCatalogDao;

	@RequestMapping(value = "/serviceCatalog/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> addService(@RequestBody String body, HttpServletRequest request) throws Exception {
		try {
			JSONObject json = new JSONObject(body);
			ServiceCatalogMapping scm = new ServiceCatalogMapping();
			scm.setOrgId(json.getInt("orgId"));
			scm.setPrice(json.getDouble("price"));
			scm.setService(json.getString("service"));
			ServiceCatalogMapping resp = serviceCatalogDao.addService(scm);
			JSONObject serviceJson = new JSONObject();
			serviceJson.put("orgId", resp.getOrgId());
			serviceJson.put("price", resp.getPrice());
			serviceJson.put("service", resp.getService());
			logger.info("Created Service Catalog successfully");
			return new ResponseEntity<Object>(serviceJson.toString(), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to create service catalog",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/serviceCatalogs", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getCatalogsByUserId(@RequestBody String body, HttpServletRequest request)
			throws Exception {
		try {
			List<ServiceCatalogMapping> catalog = serviceCatalogDao.getServicesByOrgId();
			Gson gson = new Gson();
			logger.info("Created Service Appointment successfully");
			return new ResponseEntity<Object>(gson.toJson(catalog), HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Retrieved service from the catalog successfully",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/delete/serviceCatalog/cid/{cId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> deleteCatalogById(@RequestBody String body, HttpServletRequest request,
			@PathVariable String cId) throws Exception {
		try {
			serviceCatalogDao.deleteServiceByCatalogId(Integer.parseInt(cId));
			logger.info("Deleted Service from the catalog successfully");
			return new ResponseEntity<Object>("Deleted Service from the catalog", HttpStatus.OK);
		} catch (UserException e) {
			logger.error("Failed to delete service from the catalog",e);
			List<Message> errors = new ArrayList<Message>();
			errors.add(new Message(e.getMessage()));
			return new ResponseEntity<Object>(new Errors(errors), HttpStatus.BAD_REQUEST);
		}
	}

}
