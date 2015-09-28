package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import constants.DatabaseConstants;

/**
 * A planet is used in an @Address object
 * @author Ofek | UPI: OWIT454
 *
 */

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Planet {
		@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
		@Id
		@XmlElement(name="id")
		private Long id;
		
	    @Column(name="NAME", nullable =false, length=45)
	    @XmlElement(name="name")
		private String name;
	    
	    @Column(name="SOLARSYSTEM", nullable =false, length=45)
	    @XmlElement(name="solar_system")
		private String solarSystem;
		
	    @Column(name="OFFICIALLANGUAGE", nullable =false, length=45)
	    @XmlElement(name="language")
		private String language;
		
		protected Planet() {}
	    
		public Planet(String name, String solarSystem, String language) {
			this.name = name;
			this.solarSystem = solarSystem;
			this.language = language;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getSolarSystem() {
			return solarSystem;
		}


		public void setSolarSystem(String solarSystem) {
			this.solarSystem = solarSystem;
		}


		public String getLanguage() {
			return language;
		}


		public void setLanguage(String language) {
			this.language = language;
		}


		public Long getId() {
			return id;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Planet))
	            return false;
	        if (obj == this)
	            return true;

	        Planet rhs = (Planet) obj;
	        return new EqualsBuilder().
	            append(name, rhs.name).
	            append(solarSystem, rhs.solarSystem).
	            append(language, rhs.language).
	            isEquals();
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 31). 
		            append(name).
		            append(solarSystem).
		            append(language).
		            toHashCode();
		}
		
		@Override
		public String toString() {
			DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(name);
			buffer.append(" on Solar System" );
			buffer.append(solarSystem);
			buffer.append( " Offial Language: " );
			buffer.append(language);
			
			return buffer.toString();
		}

}
