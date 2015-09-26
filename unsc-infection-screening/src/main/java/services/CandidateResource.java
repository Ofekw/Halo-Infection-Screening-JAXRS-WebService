package services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Address;
import domain.Candidate;
import domain.CandidateAssessment;
import dto.*;
import singleton.EntityManagerFactorySingleton;


/**
 *
 */
@Path("/candidates")
public class CandidateResource {
	private static final Logger logger = LoggerFactory.getLogger(CandidateResource.class);
	private EntityManagerFactory enityManagerFactory;
	private  EntityManager entityManager;
	
	public CandidateResource() {
		 enityManagerFactory = EntityManagerFactorySingleton.getInstance();
		 entityManager = enityManagerFactory.createEntityManager();
	}

	/**
	 * Adds a new Candidate to the system.
	 * @param CandidateDTO
	 *            the Candidate data included in the HTTP request body.
	 */
	@POST
	@Consumes("application/xml")
	public Response createCandidate(CandidateDTO dto) {
		logger.debug("Read candidate: " + dto);
		entityManager.getTransaction().begin();
		//persist candidate
		Candidate candidate = CandidateMapper.toDomainModel(dto);
		entityManager.persist(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();

		
		logger.debug("Created Candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("Created Candidate WITH ID: " + candidate.getId());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}
	/**
	 * Adds an address to a candidate
	 * @param dto
	 */
	
	@POST
	@Consumes("application/xml")
	@Path("{id}/address")
	public Response setAddress(@PathParam("id") long id, Address address){
		logger.debug("Read candidate for updating address: " + id);
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find( Candidate.class, id);
		candidate.setAddress(address);
		entityManager.persist(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();

		
		logger.debug("Update address for candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("The address WITH PROPERTIES: " + address.getId()+", "+address.getRoad()+", "+address.getCity()+", "+address.getCountry());
		logger.debug("The address is ON PLANET: " + address.getPlanet().getName());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}
	
	@POST
	@Consumes("application/xml")
	@Path("{id}/add/assessment")
	public Response setAddress(@PathParam("id") long id, CandidateAssessment assessment){
		logger.debug("Read candidate for updating address: " + id);
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find(Candidate.class, id);
		candidate.addAssessment(assessment);
		entityManager.persist(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();
		logger.debug("Setting assessment for candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("The assesment WITH ID: " + assessment.getId());
		logger.debug("The assesment IS INFECTED: " + assessment.isInfected());
		logger.debug("The assesment IS QUARANTINED: " + assessment.isQuarantined());
		logger.debug("The candidate was ASSESSED ON: " + assessment.getAssessmentDate());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public CandidateDTO getCandidateDTO(
			@PathParam("id") long id) {
		// Get the full Candidate object from the database.
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find( Candidate.class, id);
		logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
		CandidateDTO dto = CandidateMapper.toDto(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return dto;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public Candidate getCandidate(
			@PathParam("id") long id) {
		// Get the full Candidate object from the database.
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find( Candidate.class, id);
		logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return candidate;
	}
	
	
	
	
	@DELETE
	public void wipeAll() {
		logger.debug("WIPING DATA");
		entityManager.getTransaction().begin();
		int rs = entityManager.createQuery("delete from Candidate").executeUpdate();
		logger.debug("Removed Candidates: "+ rs);
	}

	

	protected void reloadDatabase() {
		
	}
}
