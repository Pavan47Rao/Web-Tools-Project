package com.preciouspaws.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.Doctor;
import com.preciouspaws.pojo.PetOwner;
import com.preciouspaws.pojo.User;
import com.preciouspaws.pojo.VeterinaryOrgUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author PavanRao
 *
 */

public class UserDAO extends DAO {
	
	public Doctor authenticateDoctor(String userName, String password) throws UserException {
        try {
            begin();
            
            Query q = getSession().createQuery("from Doctor where username=:userName");
            q.setString("userName", userName);
            
            List<User> list = q.list();
            if(list.isEmpty()) {
                throw new HibernateException("Username or Password is invalid");
            }
            Doctor user = (Doctor) list.get(0);
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if(!bcrypt.matches(password, user.getPassword()))
                throw new HibernateException("Username or Password is invalid");
            commit();
            close();
            return user;
        } catch (HibernateException e) {
            rollback();
            throw new UserException(e.getMessage());
        }
	}

	@SuppressWarnings("unchecked")
	public List<Doctor> getAllDoctors() throws UserException {
		try {
			Query query = getSession().createQuery("from Doctor");
			return (List<Doctor>) query.list();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public VeterinaryOrgUser getVetOrg(String userName, String password) throws UserException {
		try {
            begin();
            
            Query q = getSession().createQuery("from VeterinaryOrgUser where username=:userName");
            q.setString("userName", userName);
            
            List<User> list = q.list();
            if(list.isEmpty()) {
                throw new HibernateException("Username or Password is invalid");
            }
            VeterinaryOrgUser user = (VeterinaryOrgUser) list.get(0);
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if(!bcrypt.matches(password, user.getPassword()))
                throw new HibernateException("Username or Password is invalid");
            commit();
            close();
            return user;
        } catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<VeterinaryOrgUser> getAllVetOrgUsers() throws UserException {
		try {
			Query query = getSession().createQuery("from VeterinaryOrgUser");
			return (List<VeterinaryOrgUser>) query.getResultList();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public PetOwner getPetOwner(String userName, String password) throws UserException {
		try {
            begin();
            
            Query q = getSession().createQuery("from PetOwner where username=:userName");
            q.setString("userName", userName);
            
            List<User> list = q.list();
            if(list.isEmpty()) {
                throw new HibernateException("Username or Password is invalid");
            }
            PetOwner user = (PetOwner) list.get(0);
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if(!bcrypt.matches(password, user.getPassword()))
                throw new HibernateException("Username or Password is invalid");
            commit();
            close();
            return user;
        } catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PetOwner> getAllPetOwners() throws UserException {
		try {
			Query query = getSession().createQuery("from PetOwner");
			return (List<PetOwner>) query.getResultList();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public PetOwner getPetOwnerById(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from PetOwner where id=:id");
			query.setInteger("id", id);
			return (PetOwner) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public VeterinaryOrgUser getVetOrgById(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from VeterinaryOrgUser where id=:id");
			query.setInteger("id", id);
			return (VeterinaryOrgUser) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public Doctor getDoctorById(int id) throws UserException {
		try {
			Query query = getSession().createQuery("from Doctor where id=:id");
			query.setInteger("id", id);
			return (Doctor) query.uniqueResult();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public Doctor createDoctor(Doctor d) throws UserException {
		try {
			begin();
			Doctor doc = new Doctor();
			doc.setUserName(d.getUserName());			
			doc.setFullName(d.getFullName());
						
	        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	        String bcryptPassword = bcrypt.encode(d.getPassword());
	        doc.setPassword(bcryptPassword);
			
			getSession().save(doc);
			commit();
			close();
			return doc;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public VeterinaryOrgUser createVetOrgUser(VeterinaryOrgUser v) throws UserException {
		try {
			begin();
			VeterinaryOrgUser vet = new VeterinaryOrgUser();
			vet.setUserName(v.getUserName());
			vet.setFullName(v.getFullName());
			vet.setContactNo(v.getContactNo());
			vet.setAddress(v.getAddress());
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	        String bcryptPassword = bcrypt.encode(v.getPassword());
	        vet.setPassword(bcryptPassword);
	        
			getSession().save(vet);
			commit();
			close();
			return vet;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public PetOwner createPetOwner(PetOwner p) throws UserException {
		try {
			begin();
			PetOwner owner = new PetOwner();
			owner.setUserName(p.getUserName());
			owner.setFullName(p.getFullName());
			owner.setContactNo(p.getContactNo());
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	        String bcryptPassword = bcrypt.encode(p.getPassword());
	        owner.setPassword(bcryptPassword);
			
			getSession().save(owner);
			commit();
			close();
			return owner;
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

}
