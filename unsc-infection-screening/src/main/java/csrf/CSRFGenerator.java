package csrf;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * Basic CSRF generator as a proof of concept
 */

public class CSRFGenerator {
	
	
	public Double generateCSRF(){
		return new DateTime().getMillis()*17/14*1.2; //random algorithm to create secure token
	}
	
	public String PreGeneratedCSRF(){
		return Double.toHexString((new DateTime(123456789l).getMillis()*17)/14*1.2); //algorithm with predefined for unit tests  
	}
}
