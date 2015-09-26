package constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CascadeType;

import domain.Planet;

public enum Species {
	HUMAN("H", "Human"), 
	SHENGHELI("S", "Elite"),
	UNKNOWN("U", "Unknown");
	
	
	private String code;
	private String species;
	
	private Species(String code, String gender){
		this.code = code;
		this.species = gender;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getString(){
		return this.species;
	}

}
