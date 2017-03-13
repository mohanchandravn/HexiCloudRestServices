package com.hexicloud.portaldb.service;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;


public interface MyServices {

    public String getCookie(@QueryParam("restEndPoint") String restEndPoint, @QueryParam("input") String input);

    public Response getShapes(@QueryParam("restEndPoint") String restEndPoint, String cookie);

    public Response getInstances(@QueryParam("restEndPoint") String restEndPoint,
                                 @PathParam("container") String container, @PathParam("userName") String userName,
                                 String cookie);

    public Response getStorageVolumes(@QueryParam("restEndPoint") String restEndPoint,
                                      @PathParam("container") String container, @PathParam("userName") String userName,
                                      String cookie);


    public JSONObject getMyServices(@QueryParam("restEndPoint") String restEndPoint,
                                    @QueryParam("container") String container, @QueryParam("userName") String userName,
                                    @QueryParam("password") String password);


}
