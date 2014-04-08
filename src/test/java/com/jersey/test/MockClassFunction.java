package com.jersey.test;


import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.han.jersey.http.HTTPCall;
import com.han.jersey.http.entity.SampleOutputEntity;
import com.jersey.entity.TestInputBuilder;

public class MockClassFunction {
	@Ignore
	@Test
	public void mockFailingProgram(){
		
		HTTPCall mock = EasyMock.createMockBuilder(HTTPCall.class).addMockedMethod("callSQL").createMock();
		EasyMock.expect(mock.callSQL("SELECT * SQL")).andReturn("OK");
		EasyMock.replay(mock);
		
		TestInputBuilder tib = new TestInputBuilder.Builder().setMsg1("1").setMsg2("2").build();
		
		Response response = mock.getPostBill(tib.convert());
		SampleOutputEntity soe = (SampleOutputEntity)response.getEntity();
		Assert.assertEquals(soe.getOutput1(),"1");
	}
	
	@Test
	public void mockProgramMatches(){
		
		HTTPCall mock = EasyMock.createMockBuilder(HTTPCall.class).addMockedMethod("callSQL").createMock();
		EasyMock.expect(mock.callSQL("SELECT * SQL")).andReturn("XX");
		EasyMock.replay(mock);
		
		TestInputBuilder tib = new TestInputBuilder.Builder().setMsg1("SELECT * SQL").setMsg2("2").build();
		Response response = mock.getPostBill(tib.convert());
		SampleOutputEntity soe = (SampleOutputEntity)response.getEntity();
		Assert.assertEquals(soe.getOutput1(),"XX");
	}
	
}
