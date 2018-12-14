package sjzc.hf.miaosha.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sjzc.hf.miaosha.error.BusinessException;
import sjzc.hf.miaosha.error.EmBusinessError;
import sjzc.hf.miaosha.response.CommonReturnType;
//@ControllerAdvice//(添加这个标签时，可以不使用继承，也可对异常进行处理)
public class BaseController{
	
	
	public static final String CONTENT_TYPE="application/x-www-form-urlencoded";

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object handlerException(Exception ex,HttpServletRequest request) {
		
		HashMap<String, Object> errorData=new HashMap<String,Object>();
		// 如果抛出的是系统自定义异常则直接转换
		if (ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			errorData.put("errorCode", businessException.getErrorCode());
			errorData.put("errorMsg", businessException.getErrorMsg());
		} else {
			// 如果抛出的不是系统自定义异常则重新构造一个未知错误异常。
			errorData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
			errorData.put("errorMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
		}
		CommonReturnType commonReturnType=CommonReturnType.creat(errorData,"failure");
		System.out.println(ex.getMessage());
		return commonReturnType;

	
	}
	
		
		

}
