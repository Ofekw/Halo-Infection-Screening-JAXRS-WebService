//package nz.ac.auckland.domain.onetoone;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//import nz.ac.auckland.domain.DatabaseConstants;
//
//@Entity
//public class Address_ {
//    @Id 
//    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//    private Long _id;
//    
//    @Column(name="STREET", length=50, nullable=false)
//	private String _street;
//	
//	@Column(name="CITY", length=30, nullable=false)
//	private String _city;
//	
//	@Column(name="ZIP_CODE", length=10)
//	private String _zipCode;
//	
//	protected Address_() {}
//	
//	public Address_(String street, String city, String zipCode) {
//		_street = street;
//		_city = city;
//		_zipCode = zipCode;
//	}
//	
//	public Long getId() {
//		return _id;
//	}
//	
//	public String getStreet() {
//		return _street;
//	}
//	
//	public String getCity() {
//		return _city;
//	}
//	
//	public String getZipCode() {
//		return _zipCode;
//	}
//}
