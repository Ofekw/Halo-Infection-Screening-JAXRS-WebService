//package services;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//import nz.ac.auckland.domain.Address;
//import nz.ac.auckland.domain.Candidate;
//
//public class CandidateWorker {
//
//	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//	Session session = sessionFactory.getCurrentSession();
//
//
//	public void getCandidateById(long id){
//		try {
//			session.beginTransaction();
//
//			Candidate dbCandidate = (Candidate) session.get(Candidate.class, id);
//			System.out.println(dbCandidate.getId() + " - " + dbCandidate.getFirstname());
//
//			session.getTransaction().commit();
//		}
//		catch (HibernateException e) {
//			e.printStackTrace();
//			session.getTransaction().rollback();
//		}
//
//	}
//
//	public void getAddressById(long id){
//		try {
//			session.beginTransaction();
//
//			Address dbAddress = (Address) session.get(Address.class, id);
//			System.out.println(dbAddress.getId() + " - " + dbAddress.getAddress());
//
//			session.getTransaction().commit();
//		}
//		catch (HibernateException e) {
//			e.printStackTrace();
//			session.getTransaction().rollback();
//		}
//
//	}
//}
