package enums;

public enum SemesterType {
	FALL("F"), WINTER("S"), YEAR("Y");
	
	private final String code;
	
	private SemesterType(String code){
		this.code = code;
	}
	
	public String code(){
		return code;
	}
}
