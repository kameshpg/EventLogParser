package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;

import datamodel.Log;
import util.HibernateUtil;

public class LogDAO {

	static final Map<String, Log> eventMap = new TreeMap<String, Log>();

	public void saveOrUpdateEvents(List<Log> eventLogs) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		int count = 0;
			for (Log eventLog : eventLogs) {
				if (!eventMap.containsKey(eventLog.getId())) {
					eventMap.put(eventLog.getId(), eventLog);
				} else {
					populateEventForUpdate(eventLog);
				}

				session.saveOrUpdate(eventLog);

				if (count != 0 && count % 30 == 0) {// Just a hot sneak for batch updates/creates. frees up the cache
					session.flush();
					session.close();
				}
				count++;
			} 
		
	}

	private void populateEventForUpdate(Log eventLog) {
		Log event = eventMap.get(eventLog.getId());

		if (event.getTimestampEnd() != 0) {
			eventLog.setTimestampEnd(event.getTimestampEnd());
		} else if (event.getTimestampStart() != 0) {
			eventLog.setTimestampStart(event.getTimestampStart());
		}

		eventLog.setDuration(eventLog.getTimestampEnd() - eventLog.getTimestampStart());

		if (eventLog.getDuration() > 4)
			eventLog.setAlert(true);
	}

	public Log getLog(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Log eventLog = (Log) session.get(Log.class, id);
		session.close();
		return eventLog;
	}

	public Log deleteLog(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Log eventLog = getLog(id);
		if (eventLog != null) {
			session.delete(eventLog);
			session.flush();
		}
		session.close();
		return eventLog;
	}

	public List<Log> getAllLogs(int firstResult, int maxResult) {
		List<Log> eventLogs = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id, id_event from Log");
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		List allUsers = query.getResultList();

		// Use a lambda fn
		for (Iterator it = allUsers.iterator(); it.hasNext();) {
			Object[] eventLogObject = (Object[]) it.next();
			Log eventLog = new Log();

			eventLog.setId_event((long) eventLogObject[1]);
			eventLogs.add(eventLog);
		}
		session.close();
		return eventLogs;
	}

}
