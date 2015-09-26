//package nz.ac.auckland.domain;
//
//import java.math.BigDecimal;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity
//public class Bid {
//	@Id
//	@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//	private Long _id;
//	
//	private BigDecimal _amount;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="ITEM_ID", nullable=false)
//	protected Item _item;
//	
//	protected Bid() {}
//	
//	public Bid(Item item, BigDecimal amount) {
//		_item = item;
//		_amount = amount;
//	}
//	
//	public BigDecimal getAmount() {
//		return _amount;
//	}
//	
//	public Item getItem() {
//		return _item;
//	}
//}
