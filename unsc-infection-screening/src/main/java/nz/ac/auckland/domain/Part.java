//package nz.ac.auckland.domain;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//
//@Entity
//public class Part {
//    @Id 
//    @GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
//    private Long _id;
//    
//    private String _description;
//    
//    protected Part() {}
//    
//    public Part(String desc) {
//    	_description = desc;
//    }
//    
//    public String getDescription() {
//    	return _description;
//    }
//    
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof Part))
//            return false;
//        if (obj == this)
//            return true;
//
//        Part rhs = (Part) obj;
//        return new EqualsBuilder().
//            append(_description, rhs._description).
//            isEquals();
//	}
//	
//	@Override
//	public int hashCode() {
//		return new HashCodeBuilder(17, 31). 
//	            append(_description).
//	            toHashCode();
//	}
//}
