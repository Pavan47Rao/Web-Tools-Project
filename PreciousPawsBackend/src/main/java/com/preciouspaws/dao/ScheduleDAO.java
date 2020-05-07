/**
 * 
 */
package com.preciouspaws.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.preciouspaws.exception.UserException;
import com.preciouspaws.pojo.AvailableSchedule;

/**
 * @author pavanrao
 *
 */
public class ScheduleDAO extends DAO {

	public void addSchedules(int docId) throws UserException {
		try {
			begin();

			List<String> timings = new ArrayList<String>();
			timings.add("10:00-10:30");
			timings.add("10:30-11:00");
			timings.add("11:00-11:30");
			timings.add("11:30-12:00");
			timings.add("14:00-14:30");
			timings.add("14:30-15:00");
			timings.add("15:00-15:30");
			timings.add("15:30-16:00");

			for (int i = 0; i < 8; i++) {
				AvailableSchedule availSchedule = new AvailableSchedule();
				availSchedule.setAvailableTimings(timings.get(i));
				availSchedule.setDocId(docId);
				getSession().save(availSchedule);
				if (i % 4 == 0) { // 4, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					getSession().flush();
					getSession().clear();
				}
			}

			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void deleteSchedule(String timings, int userId) throws UserException {
		try {
			begin();
			String hql = "DELETE FROM AvailableSchedule WHERE availabletimings=:timings AND docid=:userId";
			Query query = getSession().createQuery(hql);
			query.setParameter("timings", timings);
			query.setParameter("userId", userId);
			int result = query.executeUpdate();
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new UserException(e.getMessage());
		}
	}

	public ScheduleDAO() {
		// TODO Auto-generated constructor stub
	}

}
