package domain;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import constants.DatabaseConstants;


@Entity
public class Address {
	  	@Id 
	    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
	    private Long id;
	    
	    @Column(name="ADDRESS", length=50, nullable=false)
		private String address;
		
	    @ManyToOne(cascade= {javax.persistence.CascadeType.PERSIST})
	    @JoinColumn(name="PLANETID", nullable=false)
		private Planet planet;
		
		@Column(name="COUNTRY", length=30, nullable=false)
		private String country;
		
		@Column(name="POSTCODE", length=10,nullable=true)
		private String postcode;

		@Column(name="CITY", length=30)
		private String city;
		
		@Column(name="LONGITUDE")
		private double longitude;
		
		@Column(name="LATITUDE")
		private double latitude;
		
		protected Address() {}
		
		public Address(String address, Planet planet, String country, String postcode) {
			super();
			this.address = address;
			this.planet = planet;
			this.country = country;
			this.postcode = postcode;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Planet getPlanet() {
			return planet;
		}

		public void setPlanet(Planet planet) {
			this.planet = planet;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public Long getId() {
			return id;
		}
		
}
