//package services;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.fail;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//import javax.ws.rs.core.GenericEntity;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import domain.Candidate;
//import dto.CandidateDTO;
//
//public class CandidateWebServiceTest{
//	private static final String WEB_SERVICE_URI = "http://localhost:8080/services/candidates";
//
//	private static Client client;
//
//	/**
//	 * One-time setup method that creates a Web service client.
//	 */
//	@BeforeClass
//	public static void setUpClient() {
//		client = ClientBuilder.newClient();
//	}
//
//	/**
//	 * Runs before each unit test restore Web service database. This ensures
//	 * that each test is independent; each test runs on a Web service that has
//	 * been initialised with a common set of candidates.
//	 */
//	@Before
//	public void reloadServerData() {
//		Response response = client
//				.target(WEB_SERVICE_URI).request()
//				.put(null);
//		response.close();
//
//		// Pause briefly before running any tests. Test addParoleeMovement(),
//		// for example, involves creating a timestamped value (a movement) and
//		// having the Web service compare it with data just generated with 
//		// timestamps. Joda's Datetime class has only millisecond precision, 
//		// so pause so that test-generated timestamps are actually later than 
//		// timestamped values held by the Web service.
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//		}
//	}
//
//	/**
//	 * One-time finalisation method that destroys the Web service client.
//	 */
//	@AfterClass
//	public static void destroyClient() {
//		client.close();
//	}
//
//	/**
//	 * Tests that the Web service can create a new Candidate.
//	 */
//	@Test
//	public void addCandidate() {
//		CandidateDTO dto = new CandidateDTO("A259", "Carter", new Date(17374219200000l), constants.Gender.MALE, constants.Species.HUMAN);
//		
//		Response response = client
//				.target(WEB_SERVICE_URI).request()
//				.post(Entity.xml(dto));
//		if (response.getStatus() != 201) {
//			fail("Failed to create new Candidate");
//		}
//
//		String location = response.getLocation().toString();
//		response.close();
//
//		// Query the Web service for the new Candidate.
//		CandidateDTO fromService = client.target(location).request()
//				.accept("application/xml").get(CandidateDTO.class);
//
//		// The original local Parolee object (zoran) should have a value equal
//		// to that of the Parolee object representing Zoran that is later
//		// queried from the Web service. The only exception is the value
//		// returned by getId(), because the Web service assigns this when it
//		// creates a Candidate.
//		assertEquals(dto.getLastname(), fromService.getLastname());
//		assertEquals(dto.getFirstname(), fromService.getFirstname());
//		assertEquals(dto.getGender(), fromService.getGender());
//		assertEquals(dto.getDob(), fromService.getDob());
//	}
//}
