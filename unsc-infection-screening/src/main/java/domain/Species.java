package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CascadeType;

import constants.DatabaseConstants;



@Entity
public class Species {
	@GeneratedValue(generator=DatabaseConstants.ID_GENERATOR)
	@Id
	private Long id;
	
    @Column(name="NAME", nullable =false, length=45)
	private String name;
	
	

	@ManyToOne(fetch=FetchType.LAZY, cascade= {javax.persistence.CascadeType.PERSIST})
	@JoinColumn(name="PLANETID", nullable=false)
	private Planet planet;
	
	protected Species() {};
	
	public Species(String name, Planet originPlanet) {
		this.name = name;
		this.planet = originPlanet;
	}

	
	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Planet getOriginPlanet() {
		return planet;
	}


	public void setOriginPlanet(Planet originPlanet) {
		this.planet = originPlanet;
	}

}
