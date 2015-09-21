//package dto;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//
//import constants.Gender;
//
//@XmlRootElement(name="candidate")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class CandidateDTO {
//	@XmlAttribute(name="id")
//	private long id;
//
//	@XmlElement(name="lastname")
//	private String lastname;
//
//	@XmlElement(name="firstname")
//	private String firstname;
//
//	@XmlElement(name="gender")
//	private Gender gender;
//	
//	@XmlElement(name="DOB")
//	private Date dob;
//	
//	@XmlElement(name="DOD")
//	private Date dod;
//	
//	@XmlElement(name="Specices")
//	private String specicie;
//	
//	@XmlElement(name="Address")
//	private String address;
//	
//	@XmlElement(name="Assessments")
//	private List<Assessment> assessments;
//	
//	@XmlElement(name="infected")
//	private boolean infected = false;
//	
//	public CandidateDTO(Candidate candidate) {
//		super();
//		this.id = candidate.getId();
//		this.lastname = candidate.getLastname();
//		this.firstname = candidate.getFirstname();
//		this.gender = candidate.getGender();
//		this.dob = candidate.getDob();
//		this.dod = candidate.getDod();
//		this.specicie = candidate.getSpecie().getName();
//		this.address = candidate.getCandidateAddress().getAddress();
//		this.assessments = candidate.getAssessments();
//		
//		for (Assessment ass : candidate.getAssessments()){
//			if (ass.isInfected()){
//				this.infected = true;
//				break;
//			}
//		}
//		
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getLastname() {
//		return lastname;
//	}
//
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
//
//	public String getFirstname() {
//		return firstname;
//	}
//
//	public void setFirstname(String firstname) {
//		this.firstname = firstname;
//	}
//
//	public Gender getGender() {
//		return gender;
//	}
//
//	public void setGender(Gender gender) {
//		this.gender = gender;
//	}
//
//	public Date getDob() {
//		return dob;
//	}
//
//	public void setDob(Date dob) {
//		this.dob = dob;
//	}
//
//	public Date getDod() {
//		return dod;
//	}
//
//	public void setDod(Date dod) {
//		this.dod = dod;
//	}
//
//	public String getSpecicie() {
//		return specicie;
//	}
//
//	public void setSpecicie(String specicie) {
//		this.specicie = specicie;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public List<Assessment> getAssessments() {
//		return assessments;
//	}
//
//	public void setAssessments(List<Assessment> assessments) {
//		this.assessments = assessments;
//	}
//
//	public boolean isInfected() {
//		return infected;
//	}
//
//	public void setInfected(boolean infected) {
//		this.infected = infected;
//	}
//
//
//}
