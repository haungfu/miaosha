package sjzc.hf.miaosha.response;

public class CommonReturnType {
	private String Status;
	private Object data;
	
	public static CommonReturnType creat(Object result) {
		return creat(result,"success");
	}
	
	public static CommonReturnType creat(Object result,String status) {
		CommonReturnType commonReturnType=new CommonReturnType();
		commonReturnType.setData(result);
		commonReturnType.setStatus(status);
		return commonReturnType;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
