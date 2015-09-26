package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
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
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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
		this.lastname = candidateDTO.getLastname();
		this.firstname = candidateDTO.getFirstname();
		this.gender = candidateDTO.getGender();
		this.dob = candidateDTO.getDob();
		this.dod = candidateDTO.getDod();
		this.species = candidateDTO.getSpecies();
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
    
   
}

