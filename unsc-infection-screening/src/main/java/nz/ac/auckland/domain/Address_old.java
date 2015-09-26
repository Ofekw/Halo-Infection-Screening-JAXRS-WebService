//package nz.ac.auckland.domain;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//
//@Embeddable
//public class Address_old {
//	@Column(name="STREET", length=50, nullable=false)
//	private String _street;
//	
//	@Column(name="CITY", length=30, nullable=false)
//	private String _city;
//	
//	@Column(name="ZIP_CODE", length=10)
//	private String _zipCode;
//	
//	protected Address_old() {}
//	
//	public Address_old(String street, String city, String zipCode) {
//		_street = street;
//		_city = city;
//		_zipCode = zipCode;
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
