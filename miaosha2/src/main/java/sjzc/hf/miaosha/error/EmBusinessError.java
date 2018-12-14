package sjzc.hf.miaosha.error;

public enum EmBusinessError implements CommonError{
	PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
	UNKNOWN_ERROR(10002,"未知错误"),
	USER_NOT_EXIST(20001,"用户不存在"),
	LOGIN_ERROR(20002,"登陆异常")
	;
	private int errorCode;
	
	private String errorMsg;
	
	
	
	private EmBusinessError(int errorCode,String errorMsg) {
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		
	}

	@Override
	public int getErrorCode() {
		
		return errorCode;
	}

	@Override
	public String getErrorMsg() {
		
		return errorMsg;
	}

	@Override
	public CommonError setErrorMsg(String errorMsg) {
		
		this.errorMsg=errorMsg;
		return this;
	}
	

}
