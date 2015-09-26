package services;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Candidate;

import dto.*;


/**
 *
 */
@Path("/candidates")
public class CandidateResource {
	private static final Logger logger = LoggerFactory.getLogger(CandidateResource.class);
	private EntityManagerFactory factory;
	private  EntityManager entityManager;
	
	public CandidateResource() {
		 factory = Persistence.createEntityManagerFactory("scratchPU");
		 entityManager = factory.createEntityManager();
	}

	/**
    * 
    */
	@PUT
	public void reloadData() {
		reloadDatabase();
	}

	/**
	 * Adds a new Candidate to the system.
	 * 
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

		
		logger.debug("Created Candidate WITH NAME: " + candidate.getFirstname());
		logger.debug("Created Candidate WITH ID: " + candidate.getId());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public CandidateDTO getParolee(
			@PathParam("id") long id) {
		// Get the full Candidate object from the database.
		entityManager.getTransaction().begin();
		Candidate candidate = entityManager.find( Candidate.class, id);
		logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
		CandidateDTO dto = CandidateMapper.toDto(candidate);
		entityManager.getTransaction().commit();
		
		return dto;
	}

	

	protected void reloadDatabase() {
		
	}
}
