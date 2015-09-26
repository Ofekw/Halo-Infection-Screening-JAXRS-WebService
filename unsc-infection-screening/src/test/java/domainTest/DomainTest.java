//package domainTest;
//import static org.junit.Assert.assertEquals;
//
//import java.math.BigDecimal;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.security.sasl.AuthorizeCallback;
//
//import org.hibernate.dialect.H2Dialect;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import constants.Gender;
//import constants.Species;
//import domain.Planet;
//import domain.Address;
//import domain.CandidateAssessment;
//import domain.AssessmentCenter;
//import domain.Candidate;
//
///**
// * Test class to illustrate the behaviour of JPA in generating relational 
// * schemas and SQL, and with more generally managing object persistence.
// * 
// * This class inherits from JpaTest, which manages database connectivity, 
// * JPA initialisation and takes care of clearing out the database immediately
// * prior to executing each unit test. This ensures that there are no side-
// * effects of running any tests.
// * 
// * To see the effect of any particular test, you may want to comment out the 
// * @Test annotations on other tests. You can then use the H2 console to view 
// * the effect of the test of interest.
// * 
// *
// */
//public class DomainTest extends JpaTest {
//	
//	private static Logger logger = LoggerFactory.getLogger(DomainTest.class);
//	
//	
////	@Test
//	public void persistPlanet() {
//		entityManager.getTransaction().begin();
//		
//		Planet planet = new Planet("Reach", "JR-22","English");
//		
//		logger.debug("The Planet speaks: "+planet.getLanguage());
//		
//		entityManager.persist(planet);
//
//
//		entityManager.getTransaction().commit();
//	}
//	
////	@Test
//	public void persistAndQueryAssessmentCenter(){
//		//Also test one to many relationship
//		entityManager.getTransaction().begin();
//		
//		Planet planet = new Planet("Harvest", "ZENON-12","English/Chinese");
//		
//		Address address1 = new Address("22 Rox Trc", planet, "Derna", "54321");
//		
//		AssessmentCenter assCenter1 = new AssessmentCenter(true, address1);
//		
//		Address address2 = new Address("1/32 Henry Rd", planet, "Broxnet", "85211");
//		
//		AssessmentCenter assCenter2 = new AssessmentCenter(true, address2);
//		
//		entityManager.persist(assCenter1);
//		entityManager.persist(assCenter2);
//		
//		 List<AssessmentCenter> AssessmentCenters = entityManager.createQuery("select a from AssessmentCenter a").getResultList();
//		 for(AssessmentCenter a : AssessmentCenters) {
//			 logger.debug("AssesmentCenter: " + a.getId() +", quarantine capabilities: "+ a.getQuarantineCapability());
//			 logger.debug(" With the address: " + a.getAssessmentCenterAddress().getRoad());
//		 }
//		
//		entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void CandidateAndAssessmentTest(){
//		
//		entityManager.getTransaction().begin();
//		
//		Planet planet = new Planet("Biko", "ZENON-12","English/Chinese");
//		Address address = new Address("435 Spartan Ave", planet, "Durban", "123-44");
//		
//		AssessmentCenter assCenter = new AssessmentCenter(true, address);
//		Candidate candidate = new Candidate("A259", "Carter", new Date(17374219200000l), Gender.MALE, Species.HUMAN);
//		candidate.setAddress(address);
//		CandidateAssessment assessment = new CandidateAssessment(true, true, assCenter, new Date(17374219200000l));
//		candidate.addAssessment(assessment);
//		
//		entityManager.persist(candidate);
//		
//		entityManager.persist(assessment);
//		
//		List<CandidateAssessment> ass = entityManager.createQuery("select a from CandidateAssessment a").getResultList();
//		for(CandidateAssessment a : ass) {
//			 logger.debug("Assessments: " + a.getId());
//
//		 }
//		
//		List<Candidate> Candidates = entityManager.createQuery("select c from Candidate c").getResultList();
//		for(Candidate c : Candidates) {
//			 logger.debug("CandidateName: " + c.getFirstname() +" " +c.getLastname() +"; Sex: "+ c.getGender());
//			 logger.debug("Tested on: " + c.getAssessments().get(0).getAssessmentDate() +" Is infected" +c.getAssessments().get(0).isInfected()); 
//
//		 }
//		entityManager.getTransaction().commit();
//	}
//	
//	
//	@Test
//	public void CandidateAddress(){
//		
//		entityManager.getTransaction().begin();
//		
//		
//		Planet planet = new Planet("Biko", "ZENON-12","English/Chinese");
//		Address address = new Address("213423423 Rd Ave", planet, "Durban", "123-44");
//		Candidate candidate = new Candidate("A259", "Sam", new Date(17374219200000l), Gender.MALE, Species.HUMAN);
//		candidate.setAddress(address);
//		
//		entityManager.persist(candidate);
//		entityManager.getTransaction().commit();
//		
//	}
//}