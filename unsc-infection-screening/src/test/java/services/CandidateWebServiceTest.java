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
import singleton.EntityManagerFactorySingleton;

public class CandidateWebServiceTest{
	private static final String WEB_SERVICE_URI = "http://localhost:8080/services/candidates";
	private static String CANDIDATE_URI;

	private static Client client;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@BeforeClass
	public static void setUpClient() {
		client = ClientBuilder.newClient();
	}

	/**
	 * One-time finalisation method that destroys the Web service client.
	 */
	@AfterClass
	public static void destroyClient() {
//		Response response = client
//				.target(WEB_SERVICE_URI).request()
//				.delete();
//		response.close();
		
		client.close();
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
				.target(CANDIDATE_URI+"/address").request()
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
	
//	@Test
//	public void addCandidatewithAssessment() {
//		
//		// Query the Web service for the new existing candidate.
//		Candidate fromService = client.target(CANDIDATE_URI).request()
//				.accept("application/xml").get(Candidate.class);
//		
//		Planet planet = new Planet("Mars", "Milkyway","English");
//		Address address = new Address("44-54", planet, "Durban", "123-44");
//		address.setLatitude(-4511.23);
//		address.setLongitude(125.521);
//		
//		AssessmentCenter assessmentCenter = new AssessmentCenter(true, address);
//		
//		CandidateAssessment assessment = new CandidateAssessment(true, true, assessmentCenter, fromService, new Date());
//		
//		Response response = client
//				.target(CANDIDATE_URI+"/add/assessment").request()
//				.post(Entity.xml(assessment));
//		if (response.getStatus() != 201) {
//			fail("Failed to add Address to existing Candidate");
//		}

//	}
}
