package constants;

public enum Gender {
	
	MALE("M", "Male"), 
	FEMALE("F", "Female"),
	UNKNOWN("U", "Unknown");
	
	
	private String code;
	private String gender;
	
	private Gender(String code, String gender){
		this.code = code;
		this.gender = gender;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getGender(){
		return this.gender;
	}

}
