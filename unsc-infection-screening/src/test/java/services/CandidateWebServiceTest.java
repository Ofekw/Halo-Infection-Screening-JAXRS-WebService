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

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Address;
import domain.AssessmentCenter;
import domain.Candidate;
import domain.CandidateAssessment;
import domain.Planet;
import dto.CandidateDTO;
import singleton.CandidateWorkerSingleton;
import singleton.EntityManagerFactorySingleton;
import workers.CandidateWorker;

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
	 * One-time finalisation method that destroys the Web service client.
	 */
	@After
	public void destroyClient() {
		client.close();
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
	 * Tests that the Web service can create a new Candidate.
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
		
		CANDIDATE_URI = location;

		// Query the Web service for the new Candidate.
		CandidateDTO fromService = client.target(location).request()
				.accept("application/xml").get(CandidateDTO.class);

		assertEquals(dto.getLastname(), fromService.getLastname());
		assertEquals(dto.getFirstname(), fromService.getFirstname());
		assertEquals(dto.getGender(), fromService.getGender());
		assertEquals(dto.getDob(), fromService.getDob());
	}
	
	
	@Test
	public void addCandidateWithAddress() {

		Planet planet = new Planet("Biko", "ZENON-12","English/Chinese");
		Address address = new Address("213423423 Rd Ave", planet, "Durban", "123-44");
		
		System.err.println(CANDIDATE_URI);
		
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void addCandidatewithAssessment() {
		System.err.println(CANDIDATE_URI);	
		
		
		Planet planet = new Planet("Mars", "Milkyway","English");
		Address address = new Address("44-54", planet, "Durban", "123-44");
		address.setLatitude(-4511.23);
		address.setLongitude(125.521);
		
		AssessmentCenter assessmentCenter = new AssessmentCenter(true, address);
		
		CandidateAssessment assessment1 = new CandidateAssessment(false, false, assessmentCenter, new Date(18366260400000l));
		CandidateAssessment assessment2 = new CandidateAssessment(true, true, assessmentCenter, new Date(18388036800000l));
		
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
		assertEquals(fromService.get(0).getAssessmentCenter(), assessmentCenter);
		
		//get singular persisted assessment from webservice directly
		CandidateAssessment fromServiceAssessment = 
				client.target(WEB_SERVICE_URI+"/get/assessment/"+fromService.get(0).getId()).request().accept("application/xml").get(CandidateAssessment.class);

		assertEquals(fromServiceAssessment.isInfected(), false);
		assertEquals(fromServiceAssessment.isQuarantined(), false);
	}
	
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
	
//	@Test
	public void getAllCandidates(){
		CandidateDTO dto1 = new CandidateDTO("Holbrok", "Linda", new Date(17374219502345l), constants.Gender.FEMALE, constants.Species.HUMAN);
		CandidateDTO dto2 = new CandidateDTO("117", "Carter", new Date(17374219502345l), constants.Gender.MALE, constants.Species.SHENGHELI);

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
	
}
