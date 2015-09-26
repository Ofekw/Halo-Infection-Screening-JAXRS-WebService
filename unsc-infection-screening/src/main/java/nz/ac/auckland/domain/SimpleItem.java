//package nz.ac.auckland.domain;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.CollectionTable;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.JoinColumn;
//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OrderColumn;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.CollectionId;
//
///**
// * Entity class to represent an auctionable item. SimpleItem has little state:
// * an id (database identity), a name, and a collection of image names. 
// * SimpleItem demonstrates how to use different kinds of persistent collections
// * (a set, a bag and an indexed list). 
// *
// */
//@Entity
//@Table(name="SIMPLE_ITEM")
//public class SimpleItem {
//	@Id
//	@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//	private Long _id;
//	private String _name;
//	
//	// Set up the collection of image names as a Set.
//	@ElementCollection
//	@CollectionTable(
//			name="IMAGE_NAME",
//			joinColumns = @JoinColumn(name="ITEM_ID"))
//	@Column(name="FILENAME")
//	private Set<String> _images = new HashSet<String>();
//	
//	// Set up  the collection of image names as a Bag.
//	/*@ElementCollection
//    @CollectionTable( name = "IMAGE_NAME" )
//    @Column( name = "FILENAME" )
//    @org.hibernate.annotations.CollectionId(
//            columns = @Column( name = "IMAGE_ID" ),
//            type = @org.hibernate.annotations.Type( type = "long" ),
//            generator = "ID_GENERATOR" )
//    protected Collection<String> _images = new ArrayList<String>( );*/
//	
//	// Set up the collection of image names as a List (with sequence numbers).
//	/*@ElementCollection
//    @CollectionTable( name = "IMAGE_NAME" )
//    @OrderColumn
//    @Column( name = "FILENAME" )
//    protected List<String> _images = new ArrayList<String>( );*/
//
//	protected SimpleItem() {}
//	
//	public SimpleItem(String name) {
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
//	public Collection<String> getImages() {
//		Collection<String> copyOfImages = new ArrayList<String>();
//		for(String image : _images) {
//			copyOfImages.add(image);
//		}
//		return copyOfImages;
//	}
//	
//	public void addImage(String image) {
//		_images.add(image);
//	}
//}
