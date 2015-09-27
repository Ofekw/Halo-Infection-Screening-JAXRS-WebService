package constants;

public enum Stability {
		STABLE("S", "Stable"), 
		MODERATE("M", "Moderate"),
		CRITICAL("C", "Critical");
		
		
		private String code;
		private String status;
		
		private Stability(String code, String status){
			this.code = code;
			this.status = status;
		}
		
		public String getCode(){
			return this.code;
		}
		
		public String getStatus(){
			return this.status;
		}

}
