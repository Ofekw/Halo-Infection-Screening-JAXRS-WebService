//package nz.ac.auckland.domain.onetoone;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import nz.ac.auckland.domain.JpaTest;
//
///**
// * Test class to illustrate the behaviour of JPA in generating relational 
// * schemas and SQL, and with more generally managing object persistence.
// * 
// * Most of the tests are implemented in class nz.ac.auckland.domain.DomainTest.
// * This class (OneToOneTest) is an extra test class that illustrates the effect
// * of @OneToOne declarations, used in package nz.ac.auckland.domain.onetoone.
// * The one-to-one relationships are covered in a separate package because 
// * different versions of existing domain model classes are required (the
// * nz.ac.auckland.domain. classes are annotated differently, e.g. with 
// * @OneToMany, @ManyToOne etc.).
// * 
// * This class inherits from JpaTest, which manages database connectivity, 
// * JPA initialisation and takes care of clearing out the database immediately
// * prior to executing each unit test. This ensures that there are no side-
// * effects of running any tests.
// * 
// * @author Ian Warren
// *
// */
//public class OneToOneTest extends JpaTest {
//	
//	private static Logger _logger = LoggerFactory.getLogger(OneToOneTest.class);
//
//	@Test
//	public void establishOneTopOneRelationshipUsingForeignKeyJoinColumn() {
//		entityManager.getTransaction().begin();
//		
//		User_ neil = new User_("neil", "Armstrong", "Neil");
//		Address_ moon = new Address_("Small Crater", "The Moon", "0000");
//		neil.setShippingAddress(moon);
//		
//		User_ felix = new User_("felix", "Baumgartner", "Felix");
//		Address_ space = new Address_("Balloon", "Edge of space", "0000");
//		felix.setShippingAddress(space);
//		
//		// Store Users, transitively persisting the Addresses.
//		entityManager.persist(neil);
//		entityManager.persist(felix);
//
//		entityManager.getTransaction().commit();
//	}
//	
//	@Test 
//	public void establishOneToOneOptionalRelationshipUsingJoinTable() {
//		entityManager.getTransaction().begin();
//		
//		// Create a Shipment without an Item and persist it.
//		Shipment_ shipment = new Shipment_();
//		entityManager.persist(shipment);
//		
//		// Create and persist an Item.
//		Item_ item = new Item_("1984 Ford Capri");
//		entityManager.persist(item);
//		
//		// Create a Shipment with an Item and persist it.
//		Shipment_ auctionShipment = new Shipment_(item);
//		entityManager.persist(auctionShipment);
//
//		entityManager.getTransaction().commit();
//	}
//	
//}