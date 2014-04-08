package com.jersey.entity;

import org.junit.Ignore;

import com.han.jersey.http.entity.SampleInputEntity;

@Ignore
public class TestInputBuilder {

	public static class Builder{
		
		public String msg1="";
		public String msg2="";
		private Long msg3;
		
		public Builder setMsg1(String value){
			this.msg1 = value;
			return this;
		}
		
		public Builder setMsg2(String value){
			this.msg2 = value;
			return this;
		}
		
		public TestInputBuilder build(){
			return new TestInputBuilder(msg1, msg2, msg3);
		}

		public void setMsg3(Long msg3) {
			this.msg3 = msg3;
		}

		public Long getMsg3() {
			return msg3;
		}
		
		
	}
	
	private final SampleInputEntity sie;
	
	public TestInputBuilder(String str1, String str2, Long lng3){
		sie = new SampleInputEntity();
		sie.setMsg1(str1);
		sie.setMsg2(str2);
		sie.setMsg3(lng3);
	}
	
	public SampleInputEntity convert(){
		return sie;
	}
	
}
