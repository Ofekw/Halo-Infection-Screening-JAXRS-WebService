package services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class WebApplication extends Application
{
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> classes = new HashSet<Class<?>>();

   public WebApplication()
   {
	  // Register the Resources singleton to handle HTTP requests.
	   CandidateResource cr = new CandidateResource();
      singletons.add(cr);
      
      // Register the ContextResolver class for JAXB.
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
