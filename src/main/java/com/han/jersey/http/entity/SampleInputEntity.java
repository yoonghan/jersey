package com.han.jersey.http.entity;

import javax.ws.rs.QueryParam;

public class SampleInputEntity {

	@QueryParam(value = "msg1")
	private String msg1;
	
	@QueryParam(value = "msg2")
	private String msg2;
	
	@QueryParam(value = "msg3")
	private Long msg3;

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	public String getMsg2() {
		return msg2;
	}

	public void setMsg3(Long msg3) {
		this.msg3 = msg3;
	}

	public Long getMsg3() {
		return msg3;
	}
}
