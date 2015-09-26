package workers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Species;
import domain.Address;
import domain.Candidate;
import singleton.EntityManagerFactorySingleton;

public class CandidateWorker {
	private static Logger logger = LoggerFactory.getLogger(CandidateWorker.class);
	
	// JDBC connection to the database.
	private static Connection jdbcConnection = null;

	// JPA EntityManagerFactory, used to create an EntityManager.
	private EntityManagerFactory factory = null;
	
	public CandidateWorker(EntityManagerFactory factory){
			this.factory = EntityManagerFactorySingleton.getInstance();
			}
	
	public Address getAddressById(long id){
		EntityManager em = factory.createEntityManager( );
		EntityTransaction tx = em.getTransaction( );
		tx.begin( );
		Address Address = em.find( Address.class, id);
		tx.commit();
		em.close();
		return Address;
	}
	
//	public Species getSpeciesByName(String name){
//		EntityManager em = factory.createEntityManager( );
//		EntityTransaction tx = em.getTransaction( );
//		tx.begin( );
//		Species species = null;
//		try {
//			species = (Species)em.createQuery("select e from Species e where e.name like :eName")
//					.setParameter("eName", name)
//					.getSingleResult();
//		
//		} catch (javax.persistence.NoResultException e) {
//		}
//		tx.commit();
//		em.close();
//		return species;
//	}
	
	
	public Candidate getCandidateById(long id){
		EntityManager em = factory.createEntityManager( );
		EntityTransaction tx = em.getTransaction( );
		tx.begin( );
		Candidate candidate = null;
		try {
			candidate = em.find( Candidate.class, id);
		} catch (javax.persistence.NoResultException e) {
		}
		tx.commit();
		em.close();
		return candidate;
	}
	
	
	

}
