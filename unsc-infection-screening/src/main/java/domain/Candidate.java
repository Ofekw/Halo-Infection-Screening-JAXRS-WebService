package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import constants.DatabaseConstants;
import constants.Gender;
import constants.Species;

@Entity
@Table(name = "CANDIDATE") 
public class Candidate {
	public enum AddressType { HOME, SHIPPING, BILLING };
	
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
    
    @ManyToOne(cascade= {CascadeType.PERSIST})
    @JoinColumn(name="ADDRESSID", nullable=true)
    private Address candidateAddress;
    
    @Column(name="SPECIES", nullable=false)
    @Enumerated
    private Species species;
    
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @ElementCollection
    @CollectionTable(name="CANDIDATEASSESSMENTS")
    private List<CandidateAssessment> assessments = new ArrayList<CandidateAssessment>();

    protected Candidate() {}
    
    public Candidate(String lastname, String firstname, Date dob, Gender gender, Species species) {
    	this.lastname = lastname;
    	this.firstname = firstname;
    	this.dob = dob;
    	this.gender = gender;
    	this.species = species;
    	
    	
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

	public Address getCandidateAddress() {
		return candidateAddress;
	}

	public void setCandidateAddress(Address candidateAddress) {
		this.candidateAddress = candidateAddress;
	}

	public Species getSpecie() {
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
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		
	}
    
   
}

