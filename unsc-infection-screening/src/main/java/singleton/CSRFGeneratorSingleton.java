package singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import csrf.CSRFGenerator;

public class CSRFGeneratorSingleton {
	/**
	 * Singleton is used to maintain instantiate a CSRF Generator
	 * as it is very expensive to create it.
	 * @author Ofek | UPI: OWIT454
	 *
	 */

private static CSRFGenerator generator;
	
	public CSRFGeneratorSingleton(){
		generator = new CSRFGenerator();
		}
	
	public static CSRFGenerator getInstance(){
		if (generator == null){
			generator = new CSRFGenerator();
		}
		return generator;
	}
}
