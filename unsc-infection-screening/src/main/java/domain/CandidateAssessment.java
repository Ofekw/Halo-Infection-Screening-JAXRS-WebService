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

import constants.DatabaseConstants;

import javax.persistence.JoinColumn;

@Entity
public class CandidateAssessment {

	@Id
	@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
	private Long id;


	@Column(name="INFECTED", nullable =false, length=30)
	private boolean infected;

	@Column(name="QUARANTINED", nullable =false, length=30)
	private boolean quarantined;

//    @ManyToOne(cascade= {CascadeType.PERSIST})
//    @JoinColumn(name="ASSESSMENTCENTERID", nullable=false)
//	private AssessmentCenter assessmentCenter;

	@ManyToOne
	@JoinColumn(name="CANDIDATEID", nullable=false)
	private Candidate candidate;

	@Column(name="ASSESSMENTDATE", nullable=false)
	@Temporal( TemporalType.DATE)
	private Date assessmentDate;

	protected CandidateAssessment(){};

	public CandidateAssessment(boolean infected, boolean quarantined, AssessmentCenter assessmentCenter,
			Candidate candidate, Date assessmentDate) {
		this.infected = infected;
		this.quarantined = quarantined;
//		this.assessmentCenter = assessmentCenter;
		this.candidate = candidate;
		this.assessmentDate = assessmentDate;
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
//
//	public AssessmentCenter getAssessmentCenter() {
//		return assessmentCenter;
//	}
//
//	public void setAssessmentCenter(AssessmentCenter assessmentCenter) {
//		this.assessmentCenter = assessmentCenter;
//	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public Long getId() {
		return id;
	}





}
