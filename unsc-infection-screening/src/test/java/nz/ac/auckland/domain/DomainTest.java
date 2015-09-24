package nz.ac.auckland.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.sasl.AuthorizeCallback;

import org.hibernate.dialect.H2Dialect;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Gender;
import domain.Planet;
import domain.Species;
import domain.Address;
import domain.Assessment;
import domain.AssessmentCenter;
import domain.Candidate;

/**
 * Test class to illustrate the behaviour of JPA in generating relational 
 * schemas and SQL, and with more generally managing object persistence.
 * 
 * This class inherits from JpaTest, which manages database connectivity, 
 * JPA initialisation and takes care of clearing out the database immediately
 * prior to executing each unit test. This ensures that there are no side-
 * effects of running any tests.
 * 
 * To see the effect of any particular test, you may want to comment out the 
 * @Test annotations on other tests. You can then use the H2 console to view 
 * the effect of the test of interest.
 * 
 * @author Ian Warren
 *
 */
public class DomainTest extends JpaTest {
	
	private static Logger logger = LoggerFactory.getLogger(DomainTest.class);
	
	
	@Test
	public void persistPlanet() {
		entityManager.getTransaction().begin();
		
		Planet planet = new Planet("Reach", "JR-22","English");
		
		logger.debug("The Planet speaks: "+planet.getLanguage());
		
		entityManager.persist(planet);


		entityManager.getTransaction().commit();
	}
	
	@Test
	public void persistSpeciesAndQueryIt() {
		//Also testing cascading persistence
		entityManager.getTransaction().begin();
		
		Planet planet = new Planet("Shenghelli", "X-3541","Shenghellios");
		
		Species species = new Species("Elites", planet);
		
		logger.debug("The Planet speaks: "+planet.getLanguage());
		
		entityManager.persist(species);
		
		 List<Species> allSpecies = entityManager.createQuery("select sp from Species sp").getResultList();
		 for(Species sp : allSpecies) {
			 logger.info("Retrieved: " + sp.getName());
			 logger.info(" With the planet: " + sp.getOriginPlanet().getName());
		 }

		entityManager.getTransaction().commit();
		
	}

	@Test
	public void persistAndQueryAssessmentCenter(){
		//Also test one to many relationship
		entityManager.getTransaction().begin();
		
		Planet planet = new Planet("Harvest", "ZENON-12","English/Chinese");
		
		Address address1 = new Address("22 Rox Trc", planet, "Derna", "54321");
		
		AssessmentCenter assCenter1 = new AssessmentCenter(true, address1);
		
		Address address2 = new Address("1/32 Henry Rd", planet, "Broxnet", "85211");
		
		AssessmentCenter assCenter2 = new AssessmentCenter(true, address2);
		
		entityManager.persist(assCenter1);
		entityManager.persist(assCenter2);
		
		 List<AssessmentCenter> AssessmentCenters = entityManager.createQuery("select a from AssessmentCenter a").getResultList();
		 for(AssessmentCenter a : AssessmentCenters) {
			 logger.debug("AssesmentCenter: " + a.getId() +", quarantine capabilities: "+ a.getQuarantineCapability());
			 logger.debug(" With the address: " + a.getAssessmentCenterAddress().getAddress());
		 }
		
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void CandidateAndAssessmentTest(){
		
		entityManager.getTransaction().begin();
		
		Planet planet = new Planet("Biko", "ZENON-12","English/Chinese");
		Species species = new Species("Humans", planet);
		Address address = new Address("213423423 Rd Ave", planet, "Durban", "123-44");
		
		AssessmentCenter assCenter = new AssessmentCenter(true, address);
		Candidate candidate = new Candidate("A259", "Carter", new Date(17374219200000l), Gender.MALE, species, address);
		Assessment ass = new Assessment(true, true, assCenter, candidate, new Date(17374219200000l));
		
		entityManager.persist(candidate);
		entityManager.persist(ass);
		
		List<Candidate> Candidates = entityManager.createQuery("select c from Candidate c").getResultList();
		for(Candidate c : Candidates) {
			 logger.debug("CandidateName: " + c.getFirstname() +" " +c.getLastname() +"; Sex: "+ c.getGender());
//			 logger.debug("Tested on: " + c.getAssessments().get(0).getAssessmentDate() +" Is infected" +c.getAssessments().get(0).isInfected()); 

		 }
		entityManager.getTransaction().commit();
	}
	
//	@Test
//	public void persistSimpleItem() {
//		_entityManager.getTransaction().begin();
//		
//		SimpleItem dvd = new SimpleItem("American Sniper");
//		dvd.addImage("image_675.png");
//		dvd.addImage("image_676.png");
//		dvd.addImage("image_678.png");
//		dvd.addImage("image_678.png");
//		
//		_logger.info("The DVD item has " + dvd.getImages().size());
//		
//		SimpleItem iPhone = new SimpleItem("iPhone");
//		iPhone.addImage("iPhone.jpg");
//		
//		SimpleItem speaker = new SimpleItem("Bluetooth speaker");
//
//		_entityManager.persist(dvd);
//		_entityManager.persist(iPhone);
//		_entityManager.persist(speaker);
//
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistItem() {
//		_entityManager.getTransaction().begin();
//		
//		Item dvd = new Item("American Sniper DVD");
//		dvd.addImage(new Image("DVD", "image_675.png", 640, 480));
//		dvd.addImage(new Image("DVD case", "image_676.png", 800, 600));
//		dvd.addImage(new Image("DVD booklet", "image_678.png", 1024, 768));
//		
//		Item iPhone = new Item("iPhone");
//		iPhone.addImage(new Image("DVD", "iPhone.jpg", 640, 480));
//		
//		Item speaker = new Item("Bluetooth speaker");
//
//		_entityManager.persist(dvd);
//		_entityManager.persist(iPhone);
//		_entityManager.persist(speaker);
//
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistItemWithBids() {
//		_entityManager.getTransaction().begin();
//		
//		Item dvd = new Item("American Sniper DVD");
//		dvd.addImage(new Image("DVD", "image_675.png", 640, 480));
//		dvd.addImage(new Image("DVD case", "image_676.png", 800, 600));
//		dvd.addImage(new Image("DVD booklet", "image_678.png", 1024, 768));
//		
//		Item iPhone = new Item("iPhone");
//		iPhone.addImage(new Image("DVD", "iPhone.jpg", 640, 480));
//		
//		Item speaker = new Item("Bluetooth speaker");
//
//		_entityManager.persist(dvd);
//		_entityManager.persist(iPhone);
//		_entityManager.persist(speaker);
//		
//		Bid bidOne = new Bid(dvd, new BigDecimal(18.50));
//		dvd.addBid(bidOne);
//		
//		Bid bidTwo = new Bid(dvd, new BigDecimal(20.00));
//		dvd.addBid(bidTwo);
//		
//		Bid bidThree = new Bid(dvd, new BigDecimal(22.00));
//		dvd.addBid(bidThree);
//		
//		Bid bidFour = new Bid(iPhone, new BigDecimal(350.00));
//		iPhone.addBid(bidFour);
//
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void deleteItemWithBid() {
//		_entityManager.getTransaction().begin();
//		
//		Item dvd = new Item("American Sniper DVD");
//		dvd.addImage(new Image("DVD", "image_675.png", 640, 480));
//		dvd.addImage(new Image("DVD case", "image_676.png", 800, 600));
//		dvd.addImage(new Image("DVD booklet", "image_678.png", 1024, 768));
//		
//		Item iPhone = new Item("iPhone");
//		iPhone.addImage(new Image("DVD", "iPhone.jpg", 640, 480));
//		
//		Item speaker = new Item("Bluetooth speaker");
//
//		_entityManager.persist(dvd);
//		_entityManager.persist(iPhone);
//		_entityManager.persist(speaker);
//		
//		Bid bidOne = new Bid(dvd, new BigDecimal(18.50));
//		dvd.addBid(bidOne);
//		
//		Bid bidTwo = new Bid(dvd, new BigDecimal(20.00));
//		dvd.addBid(bidTwo);
//		
//		Bid bidThree = new Bid(dvd, new BigDecimal(22.00));
//		dvd.addBid(bidThree);
//		
//		Bid bidFour = new Bid(iPhone, new BigDecimal(350.00));
//		iPhone.addBid(bidFour);
//
//		_entityManager.remove(iPhone);
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void queryBillingDetails() {
//		_entityManager.getTransaction().begin();
//		
//		BillingDetails[] accounts = new BillingDetails[5];
//		accounts[0] = new CreditCard("Amy", "4999...", "Apr-2015");
//		accounts[1] = new CreditCard("Kim", "4999...", "Mar-2017");
//		accounts[2] = new BankAccount("Pete", "50887471", "ANZ");
//		accounts[3] = new BankAccount("John", "83846883", "ASB");
//		accounts[4] = new CreditCard("Geoff", "4556...", "Dec-2016");
//		
//		for(int i = 0; i < accounts.length; i++) {
//			_entityManager.persist(accounts[i]);
//		}
//		
//		 List<BillingDetails> billingDetails = _entityManager.createQuery("select bd from BillingDetails bd").getResultList();
//		 for(BillingDetails bd : billingDetails) {
//			 _logger.info("Retrieved: " + bd.getClass().getName());
//		 }
//
//		 _entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistUserWithBillingDetails() {
//		_entityManager.getTransaction().begin();
//		
//		BillingDetails billing = new CreditCard("Amy", "4999...", "Apr-2015");
//		User amy = new User("amy", "Johnson", "Amy");
//		amy.setDefaultBillingDetails(billing);
//		amy.setAddress(AddressType.SHIPPING, new Address("Sydney Gardens", "Auckland", "1010"));
//		amy.setAddress(AddressType.BILLING, new Address("Sydney Gardens", "Auckland", "1010"));
//		amy.setAddress(AddressType.HOME, new Address("Sydney Gardens", "Auckland", "1010"));
//		
//		User neil = new User("neil", "Armstrong", "Neil");
//		neil.setDefaultBillingDetails(billing);
//		neil.setAddress(AddressType.SHIPPING, new Address("Small Crater", "The Moon", "0000"));
//		neil.setAddress(AddressType.BILLING, new Address("Small Crater", "The Moon", "0000"));
//		neil.setAddress(AddressType.HOME, new Address("Small Crater", "The Moon", "0000"));
//		
//		
//		User felix = new User("felix", "Baumgartner", "Felix");
//		felix.setAddress(AddressType.SHIPPING, new Address("Balloon", "Edge of space", "0000"));
//		felix.setAddress(AddressType.BILLING, new Address("Balloon", "Edge of space", "0000"));
//		felix.setAddress(AddressType.HOME, new Address("Balloon", "Edge of space", "0000"));
//		
//		
//		BillingDetails otherbilling = new CreditCard("Kim", "4999...", "Mar-2017");
//		
//		_entityManager.persist(billing);
//		_entityManager.persist(amy);
//		_entityManager.persist(neil);
//		_entityManager.persist(felix);
//		_entityManager.persist(otherbilling);
//		
//		List<User> users = _entityManager.createQuery("select user from User user").getResultList();
//		 for(User user : users) {
//			 BillingDetails bd = user.getDefaultBillingDetails();
//			 if(bd != null) {
//				 _logger.info("Retrieved User: " + user.getUserName() + " - " + bd.getClass().getName());
//			 } else {
//				 _logger.info("Retrieved User: " + user.getUserName() + " - no billing details");
//			 }
//		 }
//		
//		 _entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistItemWithoutBuyer() {
//		_entityManager.getTransaction().begin();
//		
//		Item dvd = new Item("American Sniper DVD");
//		
//		_entityManager.persist(dvd);
//
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistItemWithBuyer() {
//		_entityManager.getTransaction().begin();
//		
//		Item dvd = new Item("American Sniper DVD");
//		
//		BillingDetails billing = new CreditCard("NASA", "4999...", "Apr-2015");
//		
//		User neil = new User("neil", "Armstrong", "Neil");
//		
//		neil.setDefaultBillingDetails(billing);
//		neil.setAddress(AddressType.SHIPPING, new Address("Small Crater", "The Moon", "0000"));
//		neil.setAddress(AddressType.BILLING, new Address("Small Crater", "The Moon", "0000"));
//		neil.setAddress(AddressType.HOME, new Address("Small Crater", "The Moon", "0000"));
//		
//		dvd.setBuyer(neil);
//		neil.addBoughtItem(dvd);
//		
//		// Note the ordering.
//		_entityManager.persist(billing);
//		_entityManager.persist(neil);
//		_entityManager.persist(dvd);
//
//		_entityManager.getTransaction().commit();
//	}
//	
//	@Test
//	public void persistCategoriesAndItems() {
//		_entityManager.getTransaction().begin();
//		
//		Category sport = new Category("Sport");
//		Category bicycles = new Category("Bicycles");
//		
//		Item triathleteBike = new Item("Litespeed Triathlete");
//		Item kidsBike = new Item("Raleigh Chopper");
//		
//		sport.addItem(triathleteBike);
//		bicycles.addItem(triathleteBike);
//		bicycles.addItem(kidsBike);
//		
//		triathleteBike.addCategory(sport);
//		triathleteBike.addCategory(bicycles);
//		kidsBike.addCategory(bicycles);
//		
//		_entityManager.persist(sport);
//		_entityManager.persist(bicycles);
//		
//		_entityManager.getTransaction().commit();
//	}
}
