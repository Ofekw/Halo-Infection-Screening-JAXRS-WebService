package services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import singleton.CSRFGeneratorSingleton;
import singleton.EntityManagerFactorySingleton;

import java.util.HashSet;
import java.util.Set;

/**
 * @Candidate JAX-RS web application to handle the Infection Screening Service
 * @author Ofek | UPI: OWIT454
 *
 */

@ApplicationPath("/services")
public class WebApplication extends Application
{
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> classes = new HashSet<Class<?>>();

   public WebApplication()
   {
	  // Register the Resources singleton to handle HTTP requests.
	  CandidateResource cr = new CandidateResource();
	  EntityManagerFactorySingleton emf = new EntityManagerFactorySingleton();
	  CSRFGeneratorSingleton gen = new CSRFGeneratorSingleton();
	  singletons.add(emf);
	  singletons.add(gen);
      
      // Register the ContextResolver class for JAXB.
	  classes.add(cr.getClass());
      classes.add(CandidateResolver.class);
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
   
   @Override
   public Set<Class<?>> getClasses()
   {
      return classes;
   }
}
