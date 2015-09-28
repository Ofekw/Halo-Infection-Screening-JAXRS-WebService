package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import constants.DatabaseConstants;
import constants.Gender;
import constants.Species;
import dto.CandidateDTO;

@Entity
@Table(name = "CANDIDATE")
public class Candidate {
    @Id 
    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
    private Long id;

    
    @Column(name="LASTNAME", nullable =false, length=30)
    private String lastname;
    
    @Column(name="FIRSTNAME", nullable=false, length=30)
    private String firstname;
    
    @Column(name="DOB", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    
    @Column(name="DOD", nullable=true)
    @Temporal( TemporalType.DATE)
    private Date dod;
    
    @Column(name="GENDER", nullable=false)
    @Enumerated
    private Gender gender;
    
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="ADDRESSID", nullable=true)
    private Address address;
    
    @Column(name="SPECIES", nullable=false)
    @Enumerated
    private Species species;
    
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="CANDIDATE_STATUS")
	List<ClinicalStatus> statusLog = new ArrayList<ClinicalStatus>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @CollectionTable(name="ASSESSMENTS")
    private List<CandidateAssessment> assessments = new ArrayList<CandidateAssessment>();

    protected Candidate() {}
    
    public Candidate(String lastname, String firstname, Date dob, Gender gender, Species species) {
    	this.lastname = lastname;
    	this.firstname = firstname;
    	this.dob = dob;
    	this.gender = gender;
    	this.species = species;
    	
    	
    }

	public Candidate(CandidateDTO candidateDTO) {
		this.lastname = candidateDTO.getLastname();
		this.firstname = candidateDTO.getFirstname();
		this.gender = candidateDTO.getGender();
		this.dob = candidateDTO.getDob();
		this.dod = candidateDTO.getDod();
		this.species = candidateDTO.getSpecies();
		this.address = candidateDTO.getAddress();
	}
	
	public void updateCandidateDetails(CandidateDTO candidateDTO) {
		this.lastname = (candidateDTO.getLastname() != null) ? candidateDTO.getLastname() : lastname;
		this.firstname = (candidateDTO.getFirstname()!= null) ? candidateDTO.getFirstname() : firstname;
		this.gender = (candidateDTO.getGender()!= null) ? candidateDTO.getGender() : gender;
		this.dob = (candidateDTO.getDob()!= null) ? candidateDTO.getDob() : dob;
		this.dod = (candidateDTO.getDod()!= null) ? candidateDTO.getDod() : dod;
		this.species = (candidateDTO.getSpecies()!= null ? candidateDTO.getSpecies() : species);
		this.statusLog = (candidateDTO.getStatusLog()!= null ? candidateDTO.getStatusLog() : statusLog);
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDod() {
		return dod;
	}

	public void setDod(Date dod) {
		this.dod = dod;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecie(Species specie) {
		this.species = specie;
	}

	public List<CandidateAssessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<CandidateAssessment> assessments) {
		this.assessments = assessments;
	}
	

	public void addAssessment(CandidateAssessment assessment) {
		this.assessments.add(assessment);
	}
	
	public void removeAssessment(CandidateAssessment assessment) {
		this.assessments.remove(assessment);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		
	}

	public List<ClinicalStatus> getStatusLog() {
		return statusLog;
	}

	public void setStatusLog(List<ClinicalStatus> statusLog) {
		this.statusLog = statusLog;
	}
	
	public void addStatus(ClinicalStatus clinicalStatus){
		statusLog.add(clinicalStatus);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Candidate))
            return false;
        if (obj == this)
            return true;

        Candidate rhs = (Candidate) obj;
        return new EqualsBuilder().
            append(lastname, rhs.lastname).
            append(firstname, rhs.firstname).
            append(dob, rhs.dob).
            append(gender, rhs.gender).
            append(address, rhs.address).
            append(species, rhs.species).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(lastname).
	            append(firstname).
	            append(dob).
	            append(gender).
	            append(address).
	            append(species).
	            toHashCode();
	}

	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(firstname);
		buffer.append(" ");
		buffer.append(firstname);
		buffer.append(" born: ");
		buffer.append(dob);
		buffer.append(" lives at: ");
		buffer.append(address.toString());
		
		return buffer.toString();
	}
}

    
