package com.han.jersey.adapter;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.han.jersey.adapter.constants.PathLocationConstants;

/**
 * Adapter to register classes and convert threat input as GSON.
 * @author yoong.han
 *
 */
@ApplicationPath("/*")
public final class RestAdapter extends ResourceConfig{
	
	public RestAdapter() {    	
		packages(PathLocationConstants.HTTP_PACKAGE);
		register(GsonMessageBodyHandler.class);
		register(ParamExceptionHandler.class);
	}

}