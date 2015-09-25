package services;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
	private static final Logger _logger = LoggerFactory.getLogger(CandidateResource.class);
	
	private Map<Long, Candidate> candidateDB;
	private AtomicLong idCounter;

	public CandidateResource() {
		reloadDatabase();
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
		_logger.debug("Read candidate: " + dto);
		Candidate candidate = CandidateMapper.toDomainModel(dto);
		candidate.setId(idCounter.incrementAndGet());
		candidateDB.put(candidate.getId(), candidate);

		
		_logger.debug("Created Candidate: " + candidate.getFirstname());
		return Response.created(URI.create("/candidates/" + candidate.getId()))
				.build();
	}

	

	protected void reloadDatabase() {
//		candidateDB = new ConcurrentHashMap<Long, Candidate>();
//		idCounter = new AtomicLong();
//
//		long id = idCounter.incrementAndGet();
//		Address address = new Address("15", "Bermuda road", "St Johns", "Auckland", "1071");
//		Parolee parolee = new Parolee(id,
//				"Sinnen", 
//				"Oliver", 
//				Gender.MALE,
//				new LocalDate(1970, 5, 26),
//				address,
//				new Curfew(address, new LocalTime(20, 00),new LocalTime(06, 30)));
//		candidateDB.put(id, parolee);

		
		
	}
}
