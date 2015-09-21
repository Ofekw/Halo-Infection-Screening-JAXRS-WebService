package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import constants.DatabaseConstants;


@Entity
public class Planet {
		@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
		@Id
		private Long id;
		
	    @Column(name="NAME", nullable =false, length=45)
		private String name;
	    
	    @Column(name="SOLARSYSTEM", nullable =false, length=45)
		private String solarSystem;
		
	    @Column(name="OFFICIALLANGUAGE", nullable =false, length=45)
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

}
