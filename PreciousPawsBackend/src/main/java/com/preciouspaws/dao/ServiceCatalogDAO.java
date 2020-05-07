/**
 * 
 */
package com.preciouspaws.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.DoctorAppointment;
import com.preciouspaws.pojo.ServiceCatalogMapping;

/**
 * @author pavanrao
 *
 */
public class ServiceCatalogDAO extends DAO {

	public ServiceCatalogMapping addService(ServiceCatalogMapping scm) throws UserException {
		try {
			begin();
			ServiceCatalogMapping serviceCatalog = new ServiceCatalogMapping();
			serviceCatalog.setService(scm.getService());
			serviceCatalog.setPrice(scm.getPrice());
			serviceCatalog.setOrgId(scm.getOrgId());
			getSession().save(serviceCatalog);
			commit();
			close();
			return serviceCatalog;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<ServiceCatalogMapping> getServicesByCatalogId() throws UserException {
		try {
			begin();
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<Object> query = builder.createQuery(Object.class);
			Root<ServiceCatalogMapping> serviceCatalog = query.from(ServiceCatalogMapping.class);
			query.multiselect(serviceCatalog.get("service"), serviceCatalog.get("price"),
					serviceCatalog.get("catalogId"));
			@SuppressWarnings("rawtypes")
			Query query2 = getSession().createQuery(query);
			return (List<ServiceCatalogMapping>) query2.getResultList();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceCatalogMapping> getServicesByOrgId() throws UserException {
		try {
			Query query = getSession().createQuery("select service, price, catalogId from ServiceCatalogMapping");
			return (List<ServiceCatalogMapping>) query.getResultList();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void deleteServiceByCatalogId(int catalogId) throws UserException {
		try {
			begin();
			String hql = "DELETE FROM ServiceCatalogMapping WHERE catalogid=:catalogId";
			Query query = getSession().createQuery(hql);
			query.setParameter("catalogId", catalogId);
			int result = query.executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public ServiceCatalogDAO() {
		// TODO Auto-generated constructor stub
	}

}
