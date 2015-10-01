package services;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.spi.AsynchronousResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import csrf.CSRFGenerator;
import domain.Address;
import domain.AssessmentCenter;
import domain.Candidate;
import domain.CandidateAssessment;
import domain.ClinicalStatus;
import domain.Planet;
import dto.CandidateDTO;
import singleton.CSRFGeneratorSingleton;
import singleton.EntityManagerFactorySingleton;


/**
 * Candidate resource handles all HTTP requests regarding candidates as part of the JAX-RS webservice
 * @author Ofek | UPI: OWIT454
 *
 */

@Path("/candidates")
public class CandidateResource {
  private static final Logger logger = LoggerFactory.getLogger(CandidateResource.class);
  private EntityManagerFactory enityManagerFactory;
  private EntityManager entityManager;
  private CSRFGenerator csrfGenerator = CSRFGeneratorSingleton.getInstance();
  


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
   * The Candidate data included in the HTTP request body.
   */
  @POST
  @Consumes("application/xml")
  @Path("{id}/update")
  public Response updateCandidate(@PathParam("id") long id, CandidateDTO dto) {
    logger.debug("Read candidate: " + dto);
    entityManager.getTransaction().begin();
    //persist candidate
    Candidate candidate = entityManager.find(Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
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
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
    candidate.addStatus(status);
    entityManager.persist(candidate);
    logger.debug("Status details: " + status.toString());
    entityManager.getTransaction().commit();
    entityManager.close();

    logger.debug("Added status for candidate WITH NAME: " + candidate.getFirstname());
    logger.debug("The status WITH PROPERTIES: " + status.toString());
  }
  /**
   * Asynchronous getter for retrieve the most critical status from a specified candidate clinical log.
   * Request times out after 1 second
   * @param id
   * @param asyncResponse
   * @return AsynchronousResponse
   */
     @GET
     @Produces("application/xml")
     @Path("{id}/get/status")
     public AsynchronousResponse asyncGet(@PathParam("id") final long id, @Suspend(1000) final AsynchronousResponse  asyncResponse) {
       logger.debug("Asynchronously getting most critical status from the candidate status log");
       Thread t=new Thread(){
         @Override public void run(){
           try {
             //Simulate time consuming task, such as finding the most sever clinical status for a candidate
             entityManager.getTransaction().begin();
             Candidate candidate = entityManager.find( Candidate.class, id);
             if (candidate == null) {
                 logger.debug("Unable to find the candidate");
                   throw new WebApplicationException(404);
             }
             entityManager.getTransaction().commit();
             entityManager.close();
             
             ClinicalStatus mostSevereStatus = null;
             
             for (ClinicalStatus status : candidate.getStatusLog()){
               if (mostSevereStatus == null || status.getStatus().getCode() > mostSevereStatus.getStatus().getCode()){
                mostSevereStatus = status; 
               }
             }
             
             Response resp=Response.ok(mostSevereStatus).type("application/xml").build();
             asyncResponse.setResponse(resp);
           }
           catch (      Exception e) {
             e.printStackTrace();
           }
         }
       }
       ;
       t.start();
    return asyncResponse;
     }


  /**
   * Adds an address to a candidate
   * @param dto
   * @return Response
   */

  @POST
  @Consumes("application/xml")
  @Path("{id}/add/address")
  public Response setAddress(@PathParam("id") long id, Address address){
    logger.debug("Read candidate for updating address: " + id);
    entityManager.getTransaction().begin();
    Candidate candidate = entityManager.find( Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
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
  /**
   * Adds an assessment to a specified candidate
   * @param id
   * @param assessment
   * @return Response
   */
  @POST
  @Consumes("application/xml")
  @Path("{id}/add/assessment")
  public Response setAssessment(@PathParam("id") long id, CandidateAssessment assessment){
    logger.debug("Read candidate for updating address: " + id);
    entityManager.getTransaction().begin();
    Candidate candidate = entityManager.find(Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
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
  /**
   * Retrieves candidateDTO parsed as XML
   * @param id
   * @return CandidateDTO
   */
  @GET
  @Path("{id}")
  @Produces("application/xml")
  public CandidateDTO getCandidateDtoXML(
      @PathParam("id") long id) {
    // Get the full Candidate object from the database.
    entityManager.getTransaction().begin();
    Candidate candidate = entityManager.find( Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
    logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
    CandidateDTO dto = CandidateMapper.toDto(candidate);
    entityManager.getTransaction().commit();
    entityManager.close();

    return dto;
  }
  /**
   * Retrieve candidateDTO parsed as JSON rather than XML
   * @param id
   * @return CandidateDTO
   */
  @GET
  @Path("{id}")
  @Produces("application/json")
  public CandidateDTO getCandidateDtoJSON(
      @PathParam("id") long id) {
    // Get the full Candidate object from the database.
    entityManager.getTransaction().begin();
    Candidate candidate = entityManager.find( Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
    logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
    CandidateDTO dto = CandidateMapper.toDto(candidate);
    entityManager.getTransaction().commit();
    entityManager.close();

    return dto;
  }
  /**
   * Retrieve a candidate that has been cached otherwise return it and cache the response.
   * @param id
   * @param request
   * @return Response
   */
  @GET
  @Path("{id}/cached")
  @Produces("application/xml")
  public Response getCandidateChached(
      @PathParam("id") long id, @Context Request request) {
    // Get the full Candidate object from the database.
    entityManager.getTransaction().begin();
    Candidate candidate = entityManager.find( Candidate.class, id);
    if (candidate == null) {
        logger.debug("Unable to find the candidate");
          throw new WebApplicationException(404);
    }
    logger.debug("Retrived Candidate WITH ID: " + candidate.getId());
    entityManager.getTransaction().commit();
    entityManager.close();
    
    CacheControl cc = new CacheControl();
    cc.setMaxAge(10);
    
    EntityTag etag = new EntityTag(Integer.toString(candidate.hashCode()));
    ResponseBuilder builder = request.evaluatePreconditions(etag);
    logger.debug("Checking if candidate is already cached: " + candidate.getId());
      if(builder == null){
        logger.debug("cached candidate did change -> serve updated content");
          builder = Response.ok(CandidateMapper.toDto(candidate));
          builder.tag(etag);
          
      }
    return builder.build();
  }
  /**
   * Gets a specific assessment according via the assessment id
   * @param id
   * @return CandidateAssessment
   */
  @GET
  @Path("/get/assessment/{id}")
  @Produces("application/xml")
  public CandidateAssessment getAssessment(
      @PathParam("id") long id) {
    // Get the full Candidate object from the database.
    entityManager.getTransaction().begin();
    CandidateAssessment assessment = entityManager.find(CandidateAssessment.class,id);
    if (assessment == null) {
        logger.debug("Unable to find the assessment");
          throw new WebApplicationException(404);
    }
    logger.debug("Retrived assessment WITH ID: " + assessment.getId());
    entityManager.getTransaction().commit();
    entityManager.close();

    return assessment;
  }
  /**
   * Retrieves all assessments for a specified candidate
   * @param id
   * @return GenericEntity
   */
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
    
    if (assessments.isEmpty()) {
        logger.debug("Unable to find any assessments");
          throw new WebApplicationException(204);
    }
    logger.debug("Retrived all assessment FOR CANDIDATE ID: " + id);
    entityManager.getTransaction().commit();
    entityManager.close();

    GenericEntity entity = new
        GenericEntity<List<CandidateAssessment>> (assessments) { };

        return entity;
  }
  
  /**
   * Returns a @GenericEntity list containing all candidates in the db  
   * @return
   */
  @GET
  @Path("/get/all/candidates")
  @Produces("application/xml")
  @SuppressWarnings("unchecked")
  public GenericEntity<List<CandidateDTO>> getCandidates(@DefaultValue("-1")@QueryParam("size") int size) {
    // Get the all Candidate object from the database.
    entityManager.getTransaction().begin();
    List<Candidate> candidates = null;
    if (size < 0){
    	candidates = entityManager.createQuery("select c from Candidate c").getResultList();
    }else{
    	candidates = entityManager.createQuery("select c from Candidate c").setMaxResults(size).getResultList();
    }
    	if (candidates.isEmpty()) {
        logger.debug("Unable to find any candidates");
          throw new WebApplicationException(204);
    }
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
   * Method for creating a cookie, to allow operator to book-mark candidates for later review
   * @param id
   * @return Response
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
  /**
   * Gets a candidate from a cookie
   * @param cookie
   * @return candidateDTO
   */
  @GET
  @Path("/get/bookmarked")
  @Produces("application/xml")
  public CandidateDTO getBookmarkedCandidate(@CookieParam("candidate") Cookie cookie) {
      if (cookie == null) {
        logger.debug("Unable to retrieve cookie");
          throw new WebApplicationException( 404 );
      }else{
        logger.debug("Retrieving bookmarked candidate with details: " + cookie.toString());
        entityManager.getTransaction().begin();
      Candidate candidate = entityManager.find(Candidate.class, Long.parseLong(cookie.getValue()));
      entityManager.getTransaction().commit();
      entityManager.close();
      CandidateDTO dto = CandidateMapper.toDto(candidate);
          return dto;
      }
  }
  
  /**
   * Demonstrate a proof of concept for a secure connection using a CSRF token.
   * To secure the whole webserive, there would need to be an intercepter that checked the CSRF token in every request
   * A CSRF token prevents a CSRF. Cross-Site Request Forgery (CSRF) is a type of attack that occurs when a malicious 
   * Web site, email, blog, instant message, or program causes a user's Web browser to perform an unwanted action on a trusted 
   * site for which the user is currently authenticated.
   * @param CSRF cookie
   * @return Accepted or forbidden response
   */
  @GET
  @Path("/get/response/secure")
  @Produces("application/xml")
  public Response returnResponseIfConnectionSecure(@CookieParam("csrf") Cookie cookie){
	  String requestCSRF = cookie.getValue(); //get token from cookie
	  double csrf = Double.parseDouble(cookie.getValue()); //set the CSRF for the session
	  String csrfCookie = Double.toString(csrf).substring(0, 10);
	  logger.debug("CSRF from cookie: " + csrfCookie);
	  String csrfServer = Double.toString(csrfGenerator.generateCSRF()).substring(0, 10);
	  logger.debug("CSRF from server: " + csrfServer);
	  System.err.println("QUAKC2 "+csrfCookie);
	  if (csrfCookie.equals(csrfServer)){
		  logger.debug("CSRF tokens match, connection is secure");
		  return Response.status(Status.ACCEPTED).build();
	  }
	  return Response.status(Status.FORBIDDEN).build();
  }



  /**
   * Wipes the database of all info (used for testing purposes only)
   */
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
