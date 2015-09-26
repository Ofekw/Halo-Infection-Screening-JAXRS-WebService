package singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

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
