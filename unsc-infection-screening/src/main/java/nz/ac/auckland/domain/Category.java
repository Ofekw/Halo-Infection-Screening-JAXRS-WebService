//package nz.ac.auckland.domain;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//
//@Entity
//public class Category {
//	 @Id 
//	 @GeneratedValue(generator="ID_GENERATOR")
//	 private Long _id;
//	 
//	 private String _name;
//	 
//	 @ManyToMany(cascade = CascadeType.PERSIST)
//	 @JoinTable(
//		name = "CATEGORY_ITEM",
//		joinColumns = @JoinColumn(name = "CATEGORY_ID"),
//		inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
//		)
//	 private Set<Item> _items = new HashSet<Item>();
//			 
//	 protected Category() {}
//	 
//	 public Category(String name) {
//		 _name = name;
//	 }
//	 
//	 public void addItem(Item item) {
//		 _items.add(item);
//	 }
//}
