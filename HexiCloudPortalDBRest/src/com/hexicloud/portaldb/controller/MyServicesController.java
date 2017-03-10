package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.service.MyServices;

import java.math.BigInteger;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyServicesController {
    private static final Logger logger = Logger.getLogger(MyServicesController.class);

    @Autowired
    private MyServices myservice;
    
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found in the system")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception ex) {
        logger.error("Exception is :", ex);
    }
    
    @RequestMapping(value = "/services/rest/getCookie/", method = RequestMethod.POST)
    public String getCookie(@QueryParam("restEndPoint") String restEndPoint, @QueryParam("input") String input){
        
           logger.info("******* Start of getCookie() in controller ***********");
           String cookie = null;
           cookie =  myservice.getCookie(restEndPoint, input);
           logger.info("******* end of getCookie() in controller ***********");
           return cookie;
        }
    
    @RequestMapping(value = "/services/rest/getShapes/", method = RequestMethod.POST)
    public Response getShapes(@QueryParam("restEndPoint") String restEndPoint, String cookie) {

        logger.info("******* Start of getShapes() in controller ***********");
        Response response = null;        
        response = myservice.getShapes(restEndPoint, cookie);
        logger.info("******* end of getShapes() in controller ***********");
        return response;
    }
    
    
    @RequestMapping(value = "/services/rest/instance/{container}/{userName}", method = RequestMethod.POST)
    public Response getInstances(@QueryParam("restEndPoint") String restEndPoint,
                                 @PathParam("container") String container, @PathParam("userName") String userName,
                                 String cookie) {
      
        logger.info("******* Start of getInstances() in controller ***********");
        Response response = null;        
        response = myservice.getInstances(restEndPoint, container, userName, cookie);
        logger.info("******* end of getInstances() in controller ***********");
        return response;
    }
    
    @RequestMapping(value = "/services/rest/storage/volume/{container}/{userName}", method = RequestMethod.POST)
    public Response getStorageVolumes(@QueryParam("restEndPoint") String restEndPoint,@PathParam("container") String container, @PathParam("userName") String userName,
                                      String cookie) {
     
        logger.info("******* Start of getStorageVolumes() in controller ***********");
        Response response = null;      
        response = myservice.getStorageVolumes(restEndPoint, container, userName, cookie);
        logger.info("******* end of getStorageVolumes() in controller ***********");
        return response;
    }
    
    @RequestMapping(value = "/services/rest/myservices", method = RequestMethod.POST)
    public JSONObject getMyServices(@QueryParam("restEndPoint") String restEndPoint,
                                    @QueryParam("container") String container, @QueryParam("userName") String userName,
                                    @QueryParam("password") String password) {
        
        logger.info("******* Start of getMyServices() in controller ***********");
        String idcontainer = "Compute-" + container;
        String requestInput = "{\"user\":\"/" + idcontainer + "/" + userName + "\",\"password\":\"" + password + "\"}";

        //{"user":"/Compute-a23605/vignesh.babu@oracle.com","password":"Lnvb3456"}
        JSONObject finalResponse = new JSONObject();
        
        finalResponse = myservice.getMyServices(restEndPoint, container, userName, password);
        logger.info("******* end of getMyServices() in controller ***********");
        //return storageResponse;
        return finalResponse;
    }

}
