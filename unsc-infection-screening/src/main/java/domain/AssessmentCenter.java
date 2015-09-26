package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class AssessmentCenter {
		@Id
		@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
		@XmlElement(name="id")
		private Long id;
		
	    @Column(name="QUARANTINECAPABILITY", nullable =false, length=45)
	    @XmlElement(name="quarantine_capability")
		private Boolean quarantineCapability;
	    
	    @ManyToOne(cascade= {CascadeType.PERSIST})
	    @JoinColumn(name="ADDRESSID", nullable=false)
	    @XmlElement(name="address")
	    private Address assessmentCenterAddress;
	    
	    protected AssessmentCenter(){};

		public AssessmentCenter(Boolean quarantineCapability, Address assessmentCenterAddress) {
			super();
			this.quarantineCapability = quarantineCapability;
			this.assessmentCenterAddress = assessmentCenterAddress;
		}

		public Boolean getQuarantineCapability() {
			return quarantineCapability;
		}

		public void setQuarantineCapability(Boolean quarantineCapability) {
			this.quarantineCapability = quarantineCapability;
		}

		public Address getAssessmentCenterAddress() {
			return assessmentCenterAddress;
		}

		public void setAssessmentCenterAddress(Address assessmentCenterAddress) {
			this.assessmentCenterAddress = assessmentCenterAddress;
		}

		public Long getId() {
			return id;
		}
		
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof AssessmentCenter))
	            return false;
	        if (obj == this)
	            return true;

	        AssessmentCenter rhs = (AssessmentCenter) obj;
	        return new EqualsBuilder().
	            append(quarantineCapability, rhs.quarantineCapability).
	            append(assessmentCenterAddress, rhs.assessmentCenterAddress).
	            isEquals();
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 31). 
		            append(quarantineCapability).
		            append(assessmentCenterAddress).
		            toHashCode();
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(assessmentCenterAddress);
			buffer.append(" Quarantine Capability" );
			buffer.append(quarantineCapability);
			return buffer.toString();
		}
		

}
