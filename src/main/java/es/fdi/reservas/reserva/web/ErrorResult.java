package es.fdi.reservas.reserva.web;

public class ErrorResult {

	//private List<String> errores;
	private int status;
	private String msg;
	
	public ErrorResult(int codeStatus, String msg){
		this.status = codeStatus;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
}
