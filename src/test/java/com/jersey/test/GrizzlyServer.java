package com.jersey.test;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;

import com.han.jersey.adapter.GsonMessageBodyHandler;
import com.han.jersey.adapter.RestAdapter;
import com.han.jersey.adapter.constants.PathLocationConstants;

public abstract class GrizzlyServer<T> {
	
	private final int PORT;
	private final URI BASE_URI;
	private final String PATH; 
	
	public GrizzlyServer(final int port, final String path){
		this.PORT = port;
		this.PATH = path;
		this.BASE_URI = UriBuilder.fromUri(PATH).port(PORT).build();
	}
	
    /**
	 * Create plain method test.
	 */
	public enum EnumMethodType{
		POST,
		GET
	}
	
	private HttpServer server;
	private WebTarget target;
    private ResourceConfig resourceConfig = new RestAdapter();
    

    @Before
	public void setUp() throws Exception{
		setupClient();
	}
	
    @After
    public void tearDown() throws Exception {
        server.stop();
    }
	
    /**
     * Setup Client connection
     * @throws IOException
     */
	private void setupClient() throws IOException{
       ResourceConfig rc = new ResourceConfig(resourceConfig);
	   server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
	
	   server.start();
	   Client c = getClient();
	   target = c.target(BASE_URI);
	}
	
	/**
	 * Create custom client process.
	 * @return
	 */
	private Client getClient(){	
		ClientConfig clientConfig = new ClientConfig().register(GsonMessageBodyHandler.class)
		.property(ClientProperties.CONNECT_TIMEOUT, 100000)
		.property(ClientProperties.READ_TIMEOUT,    100000);
		
		return ClientBuilder.newBuilder().withConfig(clientConfig).build();
	}
	
	public Response getResponse(Entity<T> reqEntity, String location, EnumMethodType methodType){
		Response response = null;
		
		switch(methodType){
		case POST:
			response = target.path(PathLocationConstants.BILLING_PATH).path(location).request().post(reqEntity);
			break;
		case GET:
			
			//skip using resolveTemplates(templateValues)
			
			WebTarget rewriteTarget = target.path(PathLocationConstants.BILLING_PATH).path(location);
			rewriteTarget = copyQueueTarget(rewriteTarget, reqEntity);
			
			response=rewriteTarget.request().get();
			break;
		}
		
		return response;
	}
	
	public Response getResponseByGet(Map<String, Object> multiMapValues, String location){
		Response response = null;

		WebTarget rewriteTarget = target.path(PathLocationConstants.BILLING_PATH).path(location);
		
		
		for(String key: multiMapValues.keySet()){
			rewriteTarget = rewriteTarget.queryParam(key, multiMapValues.get(key));
		}
		
		response=rewriteTarget.request().get();

		return response;
	}
	
	public Response getResponseByGet(String fullURLlocation){
		Response response = null;

		response=target.path(fullURLlocation).request().get();
		
		return response;
	}
	
	private  WebTarget copyQueueTarget(WebTarget rewriteTarget, Entity<T> reqEntity) {
		
		Map<String, Object> mapped = convertEntity(reqEntity);
		
		for(String key: mapped.keySet()){
			rewriteTarget = rewriteTarget.queryParam(key, mapped.get(key));
		}
		
		return rewriteTarget;
	}

	
	public WebTarget getTarget(){
		return target;
	}
	
	public abstract Map<String, Object> convertEntity(Entity<T> entity);
}
