package singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	/**
	 * Singleton is used to maintain one @EntityManagerFactory for the lifecycle of the application
	 * as it is very expensive to create it.
	 * @author Ofek | UPI: OWIT454
	 *
	 */

private static EntityManagerFactory entityManagerFactory;
	
	public EntityManagerFactorySingleton(){
		entityManagerFactory = Persistence.createEntityManagerFactory("scratchPU");
		}
	
	public static EntityManagerFactory getInstance(){
		if (entityManagerFactory == null){
			entityManagerFactory = Persistence.createEntityManagerFactory("scratchPU");
		}
		return entityManagerFactory;
	}
}
