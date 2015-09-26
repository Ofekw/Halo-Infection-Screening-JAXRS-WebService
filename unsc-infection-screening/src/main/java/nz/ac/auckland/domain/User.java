//package nz.ac.auckland.domain;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.AttributeOverrides;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "USERS") 
//public class User {
//	public enum AddressType { HOME, SHIPPING, BILLING };
//	
//    @Id 
//    @GeneratedValue(generator="ID_GENERATOR")
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
//    @ManyToOne
//    private BillingDetails _defaultBilling;
//    
//    private Address_old _homeAddress;
//    
//    @AttributeOverrides( {
//    	@AttributeOverride(name="_street",
//    			column=@Column(name="SHIPPING_STREET", nullable=false)),
//    	@AttributeOverride(name="_city",
//    			column=@Column(name="SHIPPING_CITY", nullable=false)),		
//    	@AttributeOverride(name="_zipCode",
//    			column=@Column(name="SHIPPING_ZIP_CODE")),
//    })
//    private Address_old _shippingAddress;
//    
//    @AttributeOverrides( {
//    	@AttributeOverride(name="_street",
//    			column=@Column(name="BILLING_STREET", nullable=false)),
//    	@AttributeOverride(name="_city",
//    			column=@Column(name="BILLING_CITY", nullable=false)),		
//    	@AttributeOverride(name="_zipCode",
//    			column=@Column(name="BILLING_ZIP_CODE")),
//    })
//    private Address_old _billingAddress;
//    
//    @OneToMany(mappedBy = "_buyer")
//    private Set<Item> _boughtItems = new HashSet<Item>();
//
//    protected User() {}
//    
//    public User(String username, String lastname, String firstname) {
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
//    public Address_old getAddress(AddressType type) {
//    	if(type == AddressType.HOME) {
//    		return _homeAddress;
//    	} else if(type == AddressType.SHIPPING) {
//    		return _shippingAddress;
//    	} else {
//    		return _billingAddress;
//    	}
//    }
//    
//    public BillingDetails getDefaultBillingDetails() {
//    	return _defaultBilling;
//    }
//    
//    public void setAddress(AddressType type, Address_old address) {
//    	if(type == AddressType.HOME) {
//    		_homeAddress = address;
//    	} else if(type == AddressType.SHIPPING) {
//    		_shippingAddress = address;
//    	} else {
//    		_billingAddress = address;
//    	}
//    }
//    
//    public void setDefaultBillingDetails(BillingDetails defaultBilling) {
//    	_defaultBilling = defaultBilling;
//    }
//    
//    public void addBoughtItem(Item item) {
//    	_boughtItems.add(item);
//    }
//}
//
