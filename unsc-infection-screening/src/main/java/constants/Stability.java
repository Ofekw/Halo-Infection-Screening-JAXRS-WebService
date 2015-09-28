package constants;

public enum Stability {
		STABLE(1, "Stable"), 
		MODERATE(2, "Moderate"),
		CRITICAL(3, "Critical");
		
		
		private int code;
		private String status;
		
		private Stability(int code, String status){
			this.code = code;
			this.status = status;
		}
		
		public int getCode(){
			return this.code;
		}
		
		public String getStatus(){
			return this.status;
		}

}
