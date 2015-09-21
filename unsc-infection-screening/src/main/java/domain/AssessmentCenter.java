package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import constants.DatabaseConstants;


@Entity
public class AssessmentCenter {
		@Id
		@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
		private Long id;
		
	    @Column(name="QUARANTINECAPABILITY", nullable =false, length=45)
		private Boolean quarantineCapability;
	    
	    @ManyToOne(cascade= {CascadeType.PERSIST})
	    @JoinColumn(name="ADDRESSID", nullable=false)
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
		

}
