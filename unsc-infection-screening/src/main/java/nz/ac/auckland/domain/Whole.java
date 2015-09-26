//package nz.ac.auckland.domain;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//
//@Entity
//public class Whole {
//    @Id 
//    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//    private Long _id;
//    
//    @OneToMany(cascade=CascadeType.PERSIST)
//    @JoinColumn(
//            name = "ITEM_ID",
//            nullable = false )
//    private Set<Part> _parts = new HashSet<Part>( );
//    
//    protected Whole() {}
//    
//    
//    public void add(Part part) {
//    	_parts.add(part);
//    	System.out.println("Added part, size: " + _parts.size());
//    }
//}
