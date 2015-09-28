package services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;

import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import constants.Stability;
import domain.Address;
import domain.AssessmentCenter;
import domain.Candidate;
import domain.CandidateAssessment;
import domain.ClinicalStatus;
import domain.Planet;
import dto.CandidateDTO;
import singleton.EntityManagerFactorySingleton;
/**
 * Unit tests to insure all webservice functions work as expected
 * @author Ofek | UPI: OWIT454
 *
 */


public class CandidateWebServiceTest{
	private static final String WEB_SERVICE_URI = "http://localhost:8080/services/candidates";
	private static String CANDIDATE_URI;

	private static Client client;
	public static boolean DELETE_ALL = true;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@Before
	public void setUpClient() {
		client = ClientBuilder.newClient();
	}

	/**
	 * Finalization method that destroys the Web service client.
	 */
	@After
	public void destroyClient() {
		client.close();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}
	
	@AfterClass
	public static void deleteAllData() {
		if(DELETE_ALL ){
			client = ClientBuilder.newClient();
			Response response = client
					.target(WEB_SERVICE_URI+"/all/delete").request()
					.delete();
			response.close();
			client.close();
		}
	}

	/**
	 * Tests that the webservice can create a new Candidate.
	 */
	@Test
	public void addCandidate() {
		CandidateDTO dto = new CandidateDTO("117", "John", new Date(17374219200000l), constants.Gender.MALE, constants.Species.HUMAN);
		Response response = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(dto));
		if (response.getStatus() != 201) {
			fail("Failed to create new Candidate");
		}

		String location = response.getLocation().toString();
		response.close();


		// Query the Web service for the new Candidate.
		CandidateDTO fromService = client.target(location).request()
				.accept("application/xml").get(CandidateDTO.class);

		assertEquals(dto.getLastname(), fromService.getLastname());
		assertEquals(dto.getFirstname(), fromService.getFirstname());
		assertEquals(dto.getGender(), fromService.getGender());
		assertEquals(dto.getDob(), fromService.getDob());
		
		//Setting global URI of first candidate to be used by other tests
		CANDIDATE_URI = location;
		if(CANDIDATE_URI == null){
			fail();
		}
	}
	
	/**
	 * Tests that the webservice supports JSON request
	 */
	@Test
	public void retrieveCandidateAsJSON() {

		// Query the Web service for the existing Candidate as JSON.
		CandidateDTO jsonDTO = client.target(CANDIDATE_URI).request()
				.accept("application/json").get(CandidateDTO.class);
		
		// Query the Web service for the existing Candidate as XML.
		CandidateDTO xmlDTO = client.target(CANDIDATE_URI).request()
				.accept("application/xml").get(CandidateDTO.class);

		assertEquals(xmlDTO.getId(), jsonDTO.getId());
	}
	
	/**
	 * Tests that we are able to add and retrieve clincal status attached to a candidate
	 */
	@Test
	public void addAndRetrieveStatus(){
		ClinicalStatus status = new ClinicalStatus(Stability.STABLE, new DateTime());
		Response response = client
				.target(CANDIDATE_URI+"/add/status").request()
				.put(Entity.xml(status));
		response.close();


		// Query the Web service for the new Candidate.
		CandidateDTO fromService = client.target(CANDIDATE_URI).request()
				.accept("application/xml").get(CandidateDTO.class);
		
		assertEquals(fromService.getStatusLog().get(0), status);
	}
	
	/**
	 * Tests that there is caching support to reduce database operations
	 */
	@Test
	public void testCachedCandidate(){
		
		//Cache candidate
		Response response1 = client.target(CANDIDATE_URI+"/cached").request()
				.accept("application/xml").get(Response.class);
		String etag1 = response1.getHeaderString("ETag");
		response1.close();
		
		//get cached candidate (note it is hard to test caching server side without a client browser)
		Response response2 = client.target(CANDIDATE_URI+"/cached").request()
				.accept("application/xml").get(Response.class);
		String etag2 = response1.getHeaderString("ETag");
		response2.close();
		
		assertEquals(etag1,etag2);
		
	}
	
	/**
	 * Tests that we are able to set an address and retrieve it from a candidate
	 * An address contains another persisted entity: Planet (we are testing that this is cascadingly persisted)
	 */
	@Test
	public void addAndRetrieveCandidateWithAddress() {

		Planet planet = new Planet("Biko", "ZENON-12","English/Chinese");
		Address address = new Address("213423423 Rd Ave", planet, "Durban", "123-44");
		
		Response response = client
				.target(CANDIDATE_URI+"/add/address").request()
				.post(Entity.xml(address));
		if (response.getStatus() != 201) {
			fail("Failed to add Address to existing Candidate");
		}
		
		response.close();

		CandidateDTO fromService = client.target(CANDIDATE_URI).request()
				.accept("application/xml").get(CandidateDTO.class);
		
		//test address
		assertEquals(address.getCity(), fromService.getAddress().getCity());
		assertEquals(address.getCountry(), fromService.getAddress().getCountry());
		assertEquals(address.getRoad(), fromService.getAddress().getRoad());
		
		//test planet
		assertEquals(address.getPlanet(), fromService.getAddress().getPlanet());
	}
	/**
	 * Tests that we are able to add several assessments to a candidate through the webservice.
	 * Tests that we can retrieve all assessments linked with a candidate.
	 * Tests that we are able to retrieve individual assessments queried by their id
	 */
	@Test
	public void addAssessmentToCandidate() {
		Planet planet1 = new Planet("Mars", "Milkyway","English");
		Address address1 = new Address("44-54", planet1, "Durban", "123-44");
		address1.setLatitude(-4511.23);
		address1.setLongitude(125.521);
		AssessmentCenter assessmentCenter1 = new AssessmentCenter(true, address1);
		CandidateAssessment assessment1 = new CandidateAssessment(false, false, assessmentCenter1, new Date(18366260400000l));
		
		
		Planet planet2 = new Planet("Reach", "Epsilon Eridani system,","English");
		Address address2 = new Address("22 Tsavo Highland", planet2, "ONI Site Alpha", "788883");
		AssessmentCenter assessmentCenter2 = new AssessmentCenter(true, address2);
		CandidateAssessment assessment2 = new CandidateAssessment(true, true, assessmentCenter2, new Date(18388036800000l));
		
		Response response = client
				.target(CANDIDATE_URI+"/add/assessment").request()
				.post(Entity.xml(assessment1));
		if (response.getStatus() != 201) {
			fail("Failed to add Address to existing Candidate");
		}
		
		response.close();
		
		response = client
				.target(CANDIDATE_URI+"/add/assessment").request()
				.post(Entity.xml(assessment2));
		if (response.getStatus() != 201) {
			fail("Failed to add Address to existing Candidate");
		}
		
		response.close();
		
		//get all assessments for a candidate
		List<CandidateAssessment> fromService = client.target(CANDIDATE_URI+"/get/assessments/").request().accept("application/xml").get(new GenericType<List<CandidateAssessment>>( ){});
		assertEquals(fromService.get(0).isInfected(), false);
		assertEquals(fromService.get(0).isQuarantined(), false);
		assertEquals(fromService.get(1).isInfected(), true);
		assertEquals(fromService.get(1).isQuarantined(), true);
		assertEquals(fromService.get(0).getAssessmentCenter(), assessmentCenter1);
		assertEquals(fromService.get(1).getAssessmentCenter(), assessmentCenter2);
		
		//get singular persisted assessment from webservice directly
		CandidateAssessment fromServiceAssessment = 
				client.target(WEB_SERVICE_URI+"/get/assessment/"+fromService.get(0).getId()).request().accept("application/xml").get(CandidateAssessment.class);

		assertEquals(fromServiceAssessment.isInfected(), false);
		assertEquals(fromServiceAssessment.isQuarantined(), false);
	}
	/**
	 * Testing if we can retrieve an existing candidate, update their details and persist again using the webservice
	 */
	@Test
	public void updateCandidateDetails(){
		
		CandidateDTO fromService = client.target(CANDIDATE_URI).request().accept("application/xml").get(CandidateDTO.class);
		
		fromService.setDod(new Date(18388123200000l));
		
		Response response = client
				.target(CANDIDATE_URI+"/update").request()
				.post(Entity.xml(fromService));
		if (response.getStatus() != 201) {
			fail("Failed to update details of existing Candidate");
		}
		
		response.close();
		fromService = client.target(CANDIDATE_URI).request().accept("application/xml").get(CandidateDTO.class);
		assertEquals(fromService.getDod(),new Date(18388123200000l));
		
	}
	
	/**
	 * Testing if we can retrieve a Generic Type collection (in this case, all candidates in the database)
	 */
	
	@Test
	public void getAllCandidates(){
		CandidateDTO dto1 = new CandidateDTO("Holbrok", "Linda", new Date(17374219502345l), constants.Gender.FEMALE, constants.Species.HUMAN);
		CandidateDTO dto2 = new CandidateDTO("James", "Carter", new Date(17374219502345l), constants.Gender.MALE, constants.Species.SHENGHEILI);

		Response response = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(dto1));
		if (response.getStatus() != 201) {
			fail("Failed to create new Candidate");
		}
		response.close();
		
		response = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(dto2));
		if (response.getStatus() != 201) {
			fail("Failed to create new Candidate");
		}
		response.close();
		
		List<CandidateDTO> fromServiceCandidates = 
				client.target(WEB_SERVICE_URI+"/get/all/candidates/").request().accept("application/xml").get(new GenericType<List<CandidateDTO>>( ){});
		assertTrue(fromServiceCandidates.size() > 2 );
		
		
	}
	/**
	 * Testing if we can create a cookie that holds the id of the last candidate and then retrieve that cookie
	 * This can be used to "bookmark" or "star" a candidate for later review
	 */
	@Test
	public void bookmarkCandidateUsingCookies(){

		CandidateDTO dto = new CandidateDTO("Vadem", "Thel", new Date(1737428951000l), constants.Gender.MALE, constants.Species.SHENGHEILI);
		Response response = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(dto));
		if (response.getStatus() != 201) {
			fail("Failed to create new Candidate");
		}

		String location = response.getLocation().toString();
		response.close();
		CandidateDTO fromService = client.target(location).request()
				.accept("application/xml").get(CandidateDTO.class);

		//create a cookie for the candidate
		response = client.target(location+"/add/bookmark").request().get();
		Cookie cookie = response.getCookies().get("candidate").toCookie();
		assertEquals(cookie.getValue(), Long.toString(fromService.getId()));
		response.close();
		
		//now retrieve candidate using cookie
		CandidateDTO cookieDTO = client.target(WEB_SERVICE_URI+"/get/bookmarked").request().cookie(cookie).get(CandidateDTO.class);
		assertEquals(Long.toString(cookieDTO.getId()), cookie.getValue());
		
	}
	
	/**
	 * Testing an asynchronous call to retrieve the most critical status for a candidate
	 * This simulates a resource intensive method that may take a while to reply (ie. if the candidate has a large clinical log)
	 */
	@Test
	public void asyncGetMostCriticalStatus(){
		//add a critical status to candidate
		ClinicalStatus status1 = new ClinicalStatus(Stability.STABLE, new DateTime());
		ClinicalStatus status2 = new ClinicalStatus(Stability.CRITICAL, new DateTime());
		ClinicalStatus status3 = new ClinicalStatus(Stability.STABLE, new DateTime());
		ClinicalStatus status4 = new ClinicalStatus(Stability.MODERATE, new DateTime());
		Response response = client
				.target(CANDIDATE_URI+"/add/status").request()
				.put(Entity.xml(status1));
		response.close();
		
		 response = client
				.target(CANDIDATE_URI+"/add/status").request()
				.put(Entity.xml(status2));
		response.close();
		response = client
				.target(CANDIDATE_URI+"/add/status").request()
				.put(Entity.xml(status3));
		response.close();
		response = client
				.target(CANDIDATE_URI+"/add/status").request()
				.put(Entity.xml(status4));
		response.close();
		
		//Async call to do server side heavy processing
		Future<Response> resp = client.target(CANDIDATE_URI+"/get/status").request().async().get();
		ClinicalStatus asyncStatus = null;
		try {
			asyncStatus = resp.get().readEntity(ClinicalStatus.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail();
		}
		
		assertEquals(status2, asyncStatus);
		
			
	}
}
