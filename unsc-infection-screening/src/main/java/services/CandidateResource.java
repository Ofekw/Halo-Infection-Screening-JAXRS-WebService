package services;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Address;
import domain.AssessmentCenter;
import domain.Candidate;
import domain.CandidateAssessment;
import domain.ClinicalStatus;
import domain.Planet;
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
	 * Updates an existing Candidate to the system.
	 * @param CandidateDTO
	 *            the Candidate data included in the HTTP request body.
	 */
	@POST
	@Consumes("application/xml")
	@Path("{id}/update")
	public Response updateCandidate(@PathParam("id") long id, CandidateDTO dto) {
		logger.debug("Read candidate: " + dto);
		entityManager.getTransaction().begin();
		//persist candidate
		Candidate candidate = entityManager.find(Candidate.class, id);
		if( candidate == null ) {
			throw new WebApplicationException( 404 );}
		candidate.updateCandidateDetails(dto);
		entityManager.persist(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();


		logger.debug("Created Candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("Created Candidate WITH ID: " + candidate.getId());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}
	/**
	 * Synchronously add a clinical status to a candidate
	 * @param id
	 * @param status
	 */
	
	@PUT
	@Consumes("application/xml")
	@Path("{id}/add/status")
	public void addStatus(@PathParam("id") long id, ClinicalStatus status){
		logger.debug("Read candidate for adding status: " + id);
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find( Candidate.class, id);
		candidate.addStatus(status);
		entityManager.persist(candidate);
		logger.debug("Status details: " + status.toString());
		entityManager.getTransaction().commit();
		entityManager.close();

		logger.debug("Added status for candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("The status WITH PROPERTIES: " + status.toString());
	}
	
	   @GET
	   @Produces("application/xml")
	   @Path("{id}/get/status")
	   public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
		   logger.debug("I AM GETTING IN HERE");
           asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
           asyncResponse.setTimeoutHandler(new TimeoutHandler() {
			@Override
			public void handleTimeout(AsyncResponse ar) {
				ar.resume(
				           Response.status(Response.Status.SERVICE_UNAVAILABLE)
				                   .entity("Operation timed out")
				                   .build());
			}
		});


           String result = "two";
           asyncResponse.resume(result);
       }


	/**
	 * Adds an address to a candidate
	 * @param dto
	 */

	@POST
	@Consumes("application/xml")
	@Path("{id}/add/address")
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
		return Response.created(URI.create("/candidates/address/" + candidate.getId()))
				.build();
	}

	@POST
	@Consumes("application/xml")
	@Path("{id}/add/assessment")
	public Response setAssessment(@PathParam("id") long id, CandidateAssessment assessment){
		logger.debug("Read candidate for updating address: " + id);
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find(Candidate.class, id);
		assessment.setCandidate(candidate);
		candidate.addAssessment(assessment);
		entityManager.persist(candidate);
		entityManager.getTransaction().commit();
		entityManager.close();
		logger.debug("Setting assessment for candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("The assesment WITH ID: " + assessment.getId());
		logger.debug("The assesment IS INFECTED: " + assessment.isInfected());
		logger.debug("The assesment IS QUARANTINED: " + assessment.isQuarantined());
		logger.debug("The candidate was ASSESSED ON: " + assessment.getAssessmentDate());
		return Response.created(URI.create("/candidates/assessment" + assessment.getId()))
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
	@Path("/get/assessment/{id}")
	@Produces("application/xml")
	public CandidateAssessment getAssessment(
			@PathParam("id") long id) {
		// Get the full Candidate object from the database.
		entityManager.getTransaction().begin();
		CandidateAssessment assessment = entityManager.find(CandidateAssessment.class,id);
		logger.debug("Retrived assessment WITH ID: " + assessment.getId());
		entityManager.getTransaction().commit();
		entityManager.close();

		return assessment;
	}

	@GET
	@Path("{id}/get/assessments")
	@Produces("application/xml")
	@SuppressWarnings("unchecked")
	public GenericEntity<List<CandidateAssessment>> getAssessments(
			@PathParam("id") long id) {
		// Get the assessments for specified candidate object from the database.
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find(Candidate.class, id);
		List<CandidateAssessment> assessments = entityManager.createQuery("select a from CandidateAssessment a where a.candidate like :candidate")
				.setParameter("candidate", candidate)
				.getResultList();

		logger.debug("Retrived all assessment FOR CANDIDATE ID: " + id);
		entityManager.getTransaction().commit();
		entityManager.close();

		GenericEntity entity = new
				GenericEntity<List<CandidateAssessment>> (assessments) { };

				return entity;
	}
	
	
	@GET
	@Path("/get/all/candidates")
	@Produces("application/xml")
	@SuppressWarnings("unchecked")
	public GenericEntity<List<CandidateDTO>> getCandidates() {
		// Get the all Candidate object from the database.
		entityManager.getTransaction().begin();
		List<Candidate> candidates = entityManager.createQuery("select c from Candidate c").getResultList();
		
		List<CandidateDTO> candidatesDTO = new LinkedList<CandidateDTO>();
		
		for (Candidate c : candidates){
			candidatesDTO.add(CandidateMapper.toDto(c));
		}
		
		logger.debug("Retrived candidates");
		entityManager.getTransaction().commit();
		entityManager.close();
		GenericEntity<List<CandidateDTO>> entity = new
				GenericEntity<List<CandidateDTO>> (candidatesDTO) { };

				return entity;
	}
	
	/**
	 * Method for creating a cookie, to allow operator to bookmark candidates for later review
	 * @param id
	 * @return
	 */
	
	@GET
	@Path("{id}/add/bookmark")
	@Produces(MediaType.TEXT_PLAIN)
	public Response bookmarkCandidate(@PathParam("id") long id) {
		//Set max age of cookie to 0 for testing purposes
	    NewCookie cookie = new NewCookie("candidate", Long.toString(id));
	  
	    logger.debug("Created a cookie for: "+ id +" with details: " + cookie.toString());
	    return Response.ok("OK").cookie(cookie).build();
	}
	
	@GET
	@Path("/get/bookmark")
	@Produces(MediaType.TEXT_PLAIN)
	public Response foo(@CookieParam("candidate") Cookie cookie) {
	    if (cookie == null) {
	    	logger.debug("Unable to retrieve cookie");
	        return Response.serverError().entity("ERROR").build();
	    } else {
	    	logger.debug("Retrieving bookmarked candidate with details: " + cookie.toString());
	        return Response.ok(cookie.getValue()).build();
	    }
	}



	//Used for test perpuoses only
	@DELETE
	@Path("all/delete")
	public void wipeAll() {
			logger.debug("WIPING DATA");
			entityManager.getTransaction().begin();
			List<Candidate> candidates = entityManager.createQuery("select c from Candidate c").getResultList();
			for(Candidate c : candidates){
				logger.debug("Removing Candidate: "+ c.getId());
				entityManager.remove(c);
			}

			List<AssessmentCenter> assesmentCenters = entityManager.createQuery("select ac from AssessmentCenter ac").getResultList();
			for(AssessmentCenter ac : assesmentCenters){
				logger.debug("Removing Assessment Center: "+ ac.getId());
				entityManager.remove(ac);
			}

			List<Address> addresses= entityManager.createQuery("select ad from Address ad").getResultList();
			for( Address ad : addresses){
				logger.debug("Removing Address: "+ ad.getId());
				entityManager.remove(ad);
			}

			List<Planet> planets= entityManager.createQuery("select p from Planet p").getResultList();
			for(Planet p : planets){
				logger.debug("Removing planet: "+ p.getId());
				entityManager.remove(p);
			}

			entityManager.getTransaction().commit();
			entityManager.close();
	}

}
