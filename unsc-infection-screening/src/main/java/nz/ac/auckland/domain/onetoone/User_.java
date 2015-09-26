//package nz.ac.auckland.domain.onetoone;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//import nz.ac.auckland.domain.DatabaseConstants;
//
//
//@Entity
//public class User_ {
//	
//    @Id 
//    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//    private Long _id;
//    
//    @Column(name="USERNAME", nullable=false, length=30)
//    private String _username;
//    
//    @Column(name="LASTNAME", nullable =false, length=30)
//    private String _lastname;
//    
//    @Column(name="FIRSTNAME", nullable=false, length=30)
//    private String _firstname;
//    
//	@OneToOne(
//    		optional = false,
//    		cascade = CascadeType.PERSIST)
//    @JoinColumn(unique=true)
//	private Address_ _shippingAddress;
//	
//    protected User_() {}
//    
//    public User_(String username, String lastname, String firstname) {
//    	_username = username;
//    	_lastname = lastname;
//    	_firstname = firstname;
//    }
//    
//    public long getId() {
//        return _id;
//    }
//
//    public String getUserName() {
//        return _username;
//    }
//    
//    public String getLastname() {
//    	return _lastname;
//    }
//    
//    public String getFirstname() {
//    	return _firstname;
//    }
//    
//    public Address_ getShippingAddress() {
//    	return _shippingAddress;
//    }
//    
//    public void setShippingAddress(Address_ shippingAddress) {
//    	_shippingAddress = shippingAddress;
//    }
//}
