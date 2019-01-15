package sjzc.hf.miaosha.error;


public class BusinessException extends RuntimeException implements CommonError{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommonError commonError=EmBusinessError.SYS_ERROR;
	
	public BusinessException(CommonError commonError) {
		super();
		this.commonError=commonError;
		
	}
	public BusinessException() {
		super();
	}
	public BusinessException(String errorMsg) {
		super();
		this.commonError.setErrorMsg(errorMsg);
		
	}
	
	//自定义异常
	public BusinessException(CommonError commonError,String errorMsg) {
		super();
		this.commonError=commonError;
		this.commonError.setErrorMsg(errorMsg);
		
	}
	
	@Override
	public int getErrorCode() {
		
		return this.commonError.getErrorCode();
	}

	@Override
	public String getErrorMsg() {
		
		return this.commonError.getErrorMsg();
	}

	@Override
	public CommonError setErrorMsg(String errorMsg) {
		this.commonError.setErrorMsg(errorMsg);
		return this;
	}

}
