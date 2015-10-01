package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import constants.DatabaseConstants;

import javax.persistence.JoinColumn;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * A @CandidateAssessment stores information regarding a screening and is directly linked to an @AssessmentCenter
 * @author Ofek | UPI: OWIT454
 *
 */

public class CandidateAssessment implements Comparable<CandidateAssessment> {
	
    @Id 
    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
    @XmlElement(name="id")
    private Long id;

	@Column(name="INFECTED", nullable =false, length=30)
	@XmlElement(name="infected")
	private boolean infected;

	@Column(name="QUARANTINED", nullable =false, length=30)
	@XmlElement(name="quarantined")
	private boolean quarantined;

    @ManyToOne(cascade= {CascadeType.PERSIST})
    @JoinColumn(name="ASSESSMENTCENTERID", nullable=false)
    @XmlElement(name="assessmentcenter")
	private AssessmentCenter assessmentCenter;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CANDIDATEID")
    @XmlTransient
    private Candidate candidate;


	@Column(name="ASSESSMENTDATE", nullable=false)
	@Temporal(TemporalType.DATE)
	@XmlElement(name="date")
	private Date date;

	protected CandidateAssessment(){};

	public CandidateAssessment(boolean infected, boolean quarantined, AssessmentCenter assessmentCenter,Date assessmentDate) {
		this.infected = infected;
		this.quarantined = quarantined;
		this.assessmentCenter = assessmentCenter;
		this.date = assessmentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	public boolean isQuarantined() {
		return quarantined;
	}

	public void setQuarantined(boolean quarantined) {
		this.quarantined = quarantined;
	}

	public AssessmentCenter getAssessmentCenter() {
		return assessmentCenter;
	}

	public void setAssessmentCenter(AssessmentCenter assessmentCenter) {
		this.assessmentCenter = assessmentCenter;
	}

	public Date getAssessmentDate() {
		return date;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.date = assessmentDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CandidateAssessment))
            return false;
        if (obj == this)
            return true;

        CandidateAssessment rhs = (CandidateAssessment) obj;
        return new EqualsBuilder().
            append(date, rhs.date).
            append(infected, rhs.infected).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(date).
	            append(infected).
	            toHashCode();
	}
	
	@Override
	public int compareTo(CandidateAssessment assessment) {
		return date.compareTo(assessment.date);
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(date.toString());
		buffer.append(" @ ");
		buffer.append( " on " );
		buffer.append(candidate.getId());
		
		return buffer.toString();
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}







}
