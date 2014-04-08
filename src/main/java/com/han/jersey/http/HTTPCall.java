package com.han.jersey.http;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ParamException;

import com.han.jersey.adapter.constants.PathLocationConstants;
import com.han.jersey.http.entity.SampleInputEntity;
import com.han.jersey.http.entity.SampleOutputEntity;
import com.han.jersey.http.impl.FakeSQLCall;

@Path(PathLocationConstants.BILLING_PATH)
public class HTTPCall implements FakeSQLCall{

	/**
	 * Simple getter method to return if the service is working.
	 * 
	 * @return always OK(200) and an text/html of OK
	 */
	@GET
	@Path(PathLocationConstants.GET_PING)
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receivesTestMessage() {
		final String response = new String("OK, tu &#233;s muito bonito!");
		return Response.status(Status.OK).entity(response).build();
	}

	/**
	 * Simple getter method to return if the service is working.
	 * 
	 * @return always OK(200) and an text/html of OK
	 */
	@POST
	@Path(PathLocationConstants.POST_BILL)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPostBill(SampleInputEntity param) {
		System.out.println("Input POST sample:" + param.getMsg1() + ","
				+ param.getMsg2());

		String value = callSQL(param.getMsg1());
		
		SampleOutputEntity soe = new SampleOutputEntity();
		soe.setOutput1(value);
		soe.setOutput2(param.getMsg2());
		
		return Response.status(Status.OK).entity(soe).build();
	}

	/**
	 * Simple getter method to return if the service is working.
	 * 
	 * @return always OK(200) and an text/html of OK
	 */
	@GET
	@Path(PathLocationConstants.GET_BILL)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGetBill(@BeanParam SampleInputEntity param) throws ParamException{
		System.out.println("Input GET sample:" + param.getMsg1() + ","
				+ param.getMsg2());

		String value = callSQL(param.getMsg1());
		
		SampleOutputEntity soe = new SampleOutputEntity();
		soe.setOutput1(value);
		soe.setOutput2(value);
		
		return Response.status(Status.OK).entity(soe).build();
	}

	@Override
	public String callSQL(String message1) {
		System.out.println("I'm doing some sql. This sql however is just a sample.");
		System.out.println("Since i won't be called, i will not return anything");
		
		return message1;
	}
}
