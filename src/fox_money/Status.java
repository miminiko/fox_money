package fox_money;

public enum Status {
	PLAN, ACTUALY;
	
	public String getName(){
		switch (this){
		case PLAN : return "plan";
		case ACTUALY : return "actualy";
		}
		return null;
	}
	
	public static Status getStatus(String status){
		switch (status) {
		case "plan":
			return PLAN;
		case "actualy":
			return ACTUALY;
		}
		return null;
	}
	
}
