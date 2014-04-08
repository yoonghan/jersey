package com.jersey.test;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;

import com.han.jersey.adapter.constants.PathLocationConstants;
import com.han.jersey.http.entity.SampleInputEntity;
import com.han.jersey.http.entity.SampleOutputEntity;
import com.jersey.entity.TestInputBuilder;

public class TestHTTPJersey extends GrizzlyServer<SampleInputEntity> {

	private static final int PORT = 8080;
	private static final String PATH = "http://localhost";

	public TestHTTPJersey() {
		super(PORT, PATH);
	}

	@Test
	public void testPing() {
		String responseMsg = getTarget()
				.path(PathLocationConstants.BILLING_PATH)
				.path(PathLocationConstants.GET_PING).request()
				.get(String.class);
		Assert.assertEquals("OK, tu &#233;s muito bonito!", responseMsg);
	}

	@Test
	public void testGet() {
		SampleInputEntity tmb = new TestInputBuilder.Builder().setMsg1("1").setMsg2("a").build().convert();
		Response response = getResponse(convertMsgToEntity(tmb), PathLocationConstants.GET_BILL, EnumMethodType.GET);
		Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());
		SampleOutputEntity output = response.readEntity(SampleOutputEntity.class);
		Assert.assertEquals(output.getOutput1(), tmb.getMsg1());
	}
	
	@Test
	public void testPost() {
		SampleInputEntity tmb = new TestInputBuilder.Builder().setMsg1("1").setMsg2("a").build().convert();
		Response response = getResponse(convertMsgToEntity(tmb), PathLocationConstants.POST_BILL, EnumMethodType.POST);
		Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());
		SampleOutputEntity output = response.readEntity(SampleOutputEntity.class);
		Assert.assertEquals(output.getOutput1(), tmb.getMsg1());
	}
	
	@Test
	public void testInvalidInput() {
		Map<String, Object> invalidMsg = new HashMap<String, Object>(2);
		invalidMsg.put("msg1", "A");
		invalidMsg.put("msg2", "A");
		invalidMsg.put("msg3", "A");
		
		Response response = super.getResponseByGet(invalidMsg, PathLocationConstants.GET_BILL);
		Assert.assertEquals(response.getStatus(), Status.BAD_REQUEST.getStatusCode());
	}
		
	private Entity<SampleInputEntity> convertMsgToEntity(SampleInputEntity message){
		return Entity.entity(message, MediaType.APPLICATION_JSON);	
	}

	@Override
	public Map<String, Object> convertEntity(Entity<SampleInputEntity> entity) {
		SampleInputEntity sie = entity.getEntity();
		
		Map<String, Object> mapList = new HashMap<String, Object>(2);
		mapList.put("msg1", sie.getMsg1());
		mapList.put("msg2", sie.getMsg2());
		mapList.put("msg3", sie.getMsg3());
		
		return mapList;
	}
}
