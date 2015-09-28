package domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import constants.Stability;
import formatters.DateTimeAdapter;
/**
 * An embedded entity which is wholly depended on the @Candidate
 * @author Ofek | UPI: OWIT454
 *
 */

@Embeddable
@XmlRootElement
public class ClinicalStatus {
	
	@Column(nullable=false)
	@Enumerated
	@XmlElement(name="stability")
	private Stability stability;
	
	@XmlElement(name="time")
	@XmlJavaTypeAdapter(value=DateTimeAdapter.class)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime time;

	public ClinicalStatus(Stability stability, DateTime timestamp) {
		super();
		this.stability = stability;
		this.time = timestamp;
	}
	
	protected ClinicalStatus(){}; 

	public Stability getStatus() {
		return stability;
	}

	public void setStatus(Stability stability) {
		this.stability = stability;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTimestamp(DateTime timestamp) {
		this.time = timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClinicalStatus))
            return false;
        if (obj == this)
            return true;

        ClinicalStatus rhs = (ClinicalStatus) obj;
        return new EqualsBuilder().
            append(time, rhs.time).
            append(stability, rhs.stability).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(time).
	            append(stability).
	            toHashCode();
	}

	@Override
	public String toString() {
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(stability);
		buffer.append(" @ ");
		buffer.append(timeFormatter.print(time));
		buffer.append( " on " );
		buffer.append(dateFormatter.print(time));
		
		return buffer.toString();
	}
}
