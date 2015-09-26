//package nz.ac.auckland.domain.onetoone;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToOne;
//import javax.persistence.JoinColumn;
//
//import nz.ac.auckland.domain.DatabaseConstants;
//
//@Entity
//public class Shipment_ {
//
//	@Id
//	@GeneratedValue(generator = DatabaseConstants.ID_GENERATOR)
//	private Long _id;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinTable(
//			name = "ITEM_SHIPMENT", 
//			joinColumns = 
//				@JoinColumn(name = "SHIPMENT_ID"), 
//			inverseJoinColumns = 
//				@JoinColumn(name = "ITEM_ID", 
//							nullable = false, 
//							unique = true))
//	private Item_ _item;
//
//	protected Shipment_() {}
//
//	public Shipment_(Item_ item) {
//		_item = item;
//	}
//}
