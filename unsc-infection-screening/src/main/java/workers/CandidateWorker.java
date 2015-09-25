package workers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import nz.ac.auckland.domain.DomainTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Address;
import domain.AssessmentCenter;
import domain.Candidate;
import domain.Species;

public class CandidateWorker {
	private static Logger logger = LoggerFactory.getLogger(CandidateWorker.class);
	
	// JDBC connection to the database.
	private static Connection jdbcConnection = null;

	// JPA EntityManagerFactory, used to create an EntityManager.
	private static EntityManagerFactory factory = null;
	
	private static EntityManager entityManager = null;
	
	public CandidateWorker(){
		
		// Load H2 database driver.
				try {
					Class.forName("org.h2.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Open a connection to the database. This is used solely to delete rows
				// from any database tables and to drop the tables.
				try {
					jdbcConnection = DriverManager.getConnection(
							"jdbc:h2:~/test;mv_store=false", "sa", "sa");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				// Create the JPA EntityManagerFactory.
				factory = Persistence.createEntityManagerFactory("scratchPU");
				
	}
	
	public Address getAddressById(long id){
		EntityManager em = factory.createEntityManager( );
		EntityTransaction tx = em.getTransaction( );
		tx.begin( );
		Address Address = em.find( Address.class, id);
		tx.commit();
		em.close();
//		List<Address> Address =  entityManager.createQuery("select a from Address a where id = "+candidate.getCandidateAddress().getId()).getResultList();
		return Address;
	}
	
	public Species getSpeciesByName(String name){
		EntityManager em = factory.createEntityManager( );
		EntityTransaction tx = em.getTransaction( );
		tx.begin( );
		Species species = null;
		try {
			species = (Species)em.createQuery("select e from Species e where e.name like :eName")
					.setParameter("eName", name)
					.getSingleResult();
		
		} catch (javax.persistence.NoResultException e) {
		}
		tx.commit();
		em.close();
		return species;
	}
	
	
	public Candidate getCandidateById(long id){
		EntityManager em = factory.createEntityManager( );
		EntityTransaction tx = em.getTransaction( );
		tx.begin( );
		Candidate candidate = em.find( Candidate.class, id);
		tx.commit();
		em.close();
		return candidate;
	}
	
	
	

}
