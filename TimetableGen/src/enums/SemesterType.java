package enums;

public enum SemesterType {
	FALL("H1F"), WINTER("H1S"), YEAR("Y1Y");
	
	private final String code;
	
	private SemesterType(String code){
		this.code = code;
	}
	
	public String code(){
		return code;
	}
}
