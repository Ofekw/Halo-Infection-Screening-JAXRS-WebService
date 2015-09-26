//package nz.ac.auckland.domain;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.MappedSuperclass;
//
//@Entity
//
//// Inheritance strategy 2: table per concrete class with unions
////@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//
////Inheritance strategy 3: table per class hierarchy
////@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//
//// Inheritance strategy 4: table per class with joins
//@Inheritance(strategy = InheritanceType.JOINED)
//public abstract class BillingDetails {
//	
//	@Id
//	@GeneratedValue(generator = DatabaseConstants.ID_GENERATOR)
//	protected Long _id;
//	
//	@Column(nullable = false)
//	protected String _owner;
//	
//	protected BillingDetails() {}
//	
//	public BillingDetails(String owner) {
//		_owner = owner;
//	}
//	
//	public String getOwner() {
//		return _owner;
//	}
//}
