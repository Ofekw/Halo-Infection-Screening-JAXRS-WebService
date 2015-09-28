package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import constants.Gender;
import constants.Species;
import domain.Address;
import domain.Candidate;
import domain.CandidateAssessment;
import domain.ClinicalStatus;

/**
 * Data Transfer Object Class to represent the Candidate Object
 * We are using a DTO because it is more lightweight and does not contain candidate assessments 
 * @author Ofek | UPI: OWIT454
 *
 */

@XmlRootElement(name="candidate")
@XmlAccessorType(XmlAccessType.FIELD)
public class CandidateDTO {
	@XmlAttribute(name="id")
	private long id;

	@XmlElement(name="lastname")
	private String lastname;

	@XmlElement(name="firstname")
	private String firstname;

	@XmlElement(name="gender")
	private Gender gender;
	
	@XmlElement(name="DOB")
	private Date dob;
	
	@XmlElement(name="DOD")
	private Date dod;
	
	@XmlElement(name="specices")
	private Species species;
	
	@XmlElement(name="address")
	private Address address;
	
	@XmlElement(name="status")
	private List<ClinicalStatus> statusLog;
	
	protected CandidateDTO() {
	}
	
	public CandidateDTO(Candidate candidate) {
		
		super();
		this.id = candidate.getId();
		this.lastname = candidate.getLastname();
		this.firstname = candidate.getFirstname();
		this.gender = candidate.getGender();
		this.dob = candidate.getDob();
		this.dod = candidate.getDod();
		this.species = candidate.getSpecies();
		this.address = candidate.getAddress();
		this.statusLog = candidate.getStatusLog();
	}

	public CandidateDTO(long id, String lastname, String firstname, Date dob, Gender gender, Species species) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.dob = dob;
		this.species = species;
	}
	
	public CandidateDTO(String lastname, String firstname, Date dob, Gender gender, Species species) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.dob = dob;
		this.species = species;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public Species getSpecies() {
		return species;
	}

	public void setSpecicie(Species species) {
		this.species = species;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
	
	public void setSpecies(Species species) {
		this.species = species;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CandidateDTO))
            return false;
        if (obj == this)
            return true;

        CandidateDTO rhs = (CandidateDTO) obj;
        return new EqualsBuilder().
            append(lastname, rhs.lastname).
            append(firstname, rhs.firstname).
            append(dob.getTime(), rhs.dob.getTime()).
            append(gender.getCode(), rhs.gender.getCode()).
            append(species.getCode(), rhs.species.getCode()).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(lastname).
	            append(firstname).
	            append(dob.getTime()).
	            append(gender.getCode()).
	            append(species.getCode()).
	            toHashCode();
	}

	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(firstname);
		buffer.append(" ");
		buffer.append(lastname);
		buffer.append(" born: ");
		buffer.append(dob);
		buffer.append(" lives at: ");
		buffer.append(address);
		
		return buffer.toString();
	}
}
