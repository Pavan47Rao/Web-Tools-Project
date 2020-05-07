/**
 * 
 */
package com.preciouspaws.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.DoctorAppointment;
import com.preciouspaws.pojo.Medication;
import com.preciouspaws.pojo.ServiceAppointment;

/**
 * @author pavanrao
 *
 */

public class AppointmentDAO extends DAO {

	public ServiceAppointment createServiceAppointment(ServiceAppointment app) throws UserException {
		try {
			begin();
			getSession().save(app);
			commit();
			close();
			return app;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public DoctorAppointment createDoctorAppointment(DoctorAppointment app) throws UserException {
		try {
			begin();
			getSession().save(app);
			commit();
			close();
			return app;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void updateDoctorAppointment(DoctorAppointment app) throws UserException {
		try {
			begin();
			String hql = "UPDATE Appointment set status=:status WHERE appointmentid=:appointmentid";
			Query query = getSession().createQuery(hql);
			query.setParameter("status", app.getStatus());
			query.setParameter("appointmentid", app.getAppointmentId());
			int result = query.executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public DoctorAppointment getDoctorAppointmentById(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type AND appointmentId=:id");
			query.setInteger("id", id);
			query.setString("type", "DoctorAppointment");
			return (DoctorAppointment) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<DoctorAppointment> getDoctorAppointmentsByDoctorId(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type AND doctorid=:id");
			query.setInteger("id", id);
			query.setString("type", "DoctorAppointment");
			return (List<DoctorAppointment>) query.list();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public Medication getMedicationByAppointmentId(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from Medication where docappointmentid=:id");
			query.setInteger("id", id);
			return (Medication) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public Medication createMedication(Medication m, int id) throws UserException {
		try {
			begin();
			DoctorAppointment docapp = getDoctorAppointmentById(id);
			m.setDocAppointment(docapp);
			getSession().save(m);
			commit();
			close();
			return m;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public void updateMedication(Medication m, int appId) throws UserException {
		try {
			begin();
			
			String hql = "UPDATE Medication set treatment=:treatment WHERE docappointmentid=:appid";
			Query query = getSession().createQuery(hql);
			query.setParameter("treatment", m.getTreatment());
			query.setParameter("appid", appId);
			int result = query.executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<ServiceAppointment> getServiceAppointments() throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type");
			query.setString("type", "ServiceAppointment");
			return (List<ServiceAppointment>) query.list();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<ServiceAppointment> getServiceAppointmentsByOwnerId(int ownerId) throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type AND ownerid=:ownerId");
			query.setString("type", "ServiceAppointment");
			query.setInteger("ownerId", ownerId);
			return (List<ServiceAppointment>) query.list();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<DoctorAppointment> getDoctorAppointmentsByOwnerId(int ownerId) throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type AND ownerid=:ownerId");
			query.setString("type", "DoctorAppointment");
			query.setInteger("ownerId", ownerId);
			return (List<DoctorAppointment>) query.list();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public ServiceAppointment getServiceAppointmentById(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from Appointment where DTYPE=:type AND appointmentId=:id");
			query.setInteger("id", id);
			query.setString("type", "ServiceAppointment");
			return (ServiceAppointment) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public void updateServiceAppointment(int id, String status) throws UserException {
		try {
			begin();
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaUpdate<ServiceAppointment> criteria = builder.createCriteriaUpdate(ServiceAppointment.class);
			Root<ServiceAppointment> root = criteria.from(ServiceAppointment.class);
			criteria.set(root.get("status"), status);
			criteria.where(builder.equal(root.get("appointmentId"), id));
			getSession().createQuery(criteria).executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public AppointmentDAO() {
		// TODO Auto-generated constructor stub
	}

}
