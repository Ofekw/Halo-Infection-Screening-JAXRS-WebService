package domain;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import constants.DatabaseConstants;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	  	@Id 
	    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
	  	@XmlElement(name="id")
	    private Long id;
	    
	    @Column(name="ADDRESS", length=50, nullable=false)
	    @XmlElement(name="address")
		private String address;
		
	    @ManyToOne(cascade= {javax.persistence.CascadeType.PERSIST})
	    @JoinColumn(name="PLANETID", nullable=false)
	    @XmlElement(name="planet")
		private Planet planet;
		
		@Column(name="COUNTRY", length=30, nullable=false)
		@XmlElement(name="country")
		private String country;
		
		@Column(name="POSTCODE", length=10,nullable=true)
		@XmlElement(name="postcode")
		private String postcode;

		@Column(name="CITY", length=30)
		@XmlElement(name="city")
		private String city;
		
		@Column(name="LONGITUDE")
		@XmlElement(name="longitude")
		private double longitude;
		
		@Column(name="LATITUDE")
		@XmlElement(name="latitude")
		private double latitude;
		
		protected Address() {}
		
		public Address(String address, Planet planet, String country, String postcode) {
			super();
			this.address = address;
			this.planet = planet;
			this.country = country;
			this.postcode = postcode;
		}

		public String getRoad() {
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
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Address))
	            return false;
	        if (obj == this)
	            return true;

	        Address rhs = (Address) obj;
	        return new EqualsBuilder().
	            append(address, rhs.address).
	            append(planet, rhs.planet).
	            append(country, rhs.country).
	            append(postcode, rhs.postcode).
	            isEquals();
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 31). 
		            append(address).
		            append(planet).
		            append(country).
		            append(postcode).
		            toHashCode();
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(address);
			buffer.append(" Country: ");
			buffer.append(country);
			buffer.append(" City: ");
			buffer.append(city);
			buffer.append(" Postcode: ");
			buffer.append(postcode);
			buffer.append( " on Planet: " );
			buffer.append(planet.getName());
			
			return buffer.toString();
		}
		
}
