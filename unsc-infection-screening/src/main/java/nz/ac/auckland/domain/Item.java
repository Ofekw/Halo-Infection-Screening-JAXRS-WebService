//package nz.ac.auckland.domain;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.CollectionTable;
//import javax.persistence.Column;
//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.JoinColumn;
//
//@Entity
//public class Item {
//	@Id
//	@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//	private Long _id;
//	private String _name;
//	
//	// Set up the collection of image names as a Set.
//	@ElementCollection
//	@CollectionTable(name="IMAGE")
//	private Set<Image> _images = new HashSet<Image>();
//	
//	@OneToMany(mappedBy = "_item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//	private Set<Bid> _bids = new HashSet<Bid>();
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinTable(name = "ITEM_BUYER",
//			joinColumns = 
//				@JoinColumn(name = "ITEM_ID"),
//			inverseJoinColumns =
//				@JoinColumn(nullable = false)
//	)
//	private User _buyer;
//	
//	@ManyToMany(mappedBy = "_items")
//	private Set<Category> _categories = new HashSet<Category>();
//
//	protected Item() {}
//	
//	public Item(String name) {
//		_name = name;
//	}
//	
//	public Long getId() {
//		return _id;
//	}
//	
//	public String getName() {
//		return _name;
//	}
//	
//	public Collection<Image> getImages() {
//		return Collections.unmodifiableSet(_images);
//	}
//	
//	public User getBuyer() {
//		return _buyer;
//	}
//	
//	public void addImage(Image image) {
//		_images.add(image);
//	}
//	
//	public void addBid(Bid bid) {
//		_bids.add(bid);
//	}
//	
//	public void setBuyer(User buyer) {
//		_buyer = buyer;
//	}
//	
//	public void addCategory(Category category) {
//		_categories.add(category);
//	}
//}
