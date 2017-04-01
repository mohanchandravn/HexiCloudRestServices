package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.service.MyServices;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyServicesController {
    private static final Logger logger = Logger.getLogger(MyServicesController.class);

    @Autowired
    private MyServices myservice;


    @RequestMapping(value = "/services/rest/getCookie/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public String getCookie(@QueryParam("restEndPoint") String restEndPoint, @QueryParam("input") String input) {

        logger.info("******* Start of getCookie() in controller ***********");
        String cookie = null;
        cookie = myservice.getCookie(restEndPoint, input);
        logger.info("******* end of getCookie() in controller ***********");
        return cookie;
    }

    @RequestMapping(value = "/services/rest/getShapes/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public Response getShapes(@QueryParam("restEndPoint") String restEndPoint, String cookie) {

        logger.info("******* Start of getShapes() in controller ***********");
        Response response = null;
        response = myservice.getShapes(restEndPoint, cookie);
        logger.info("******* end of getShapes() in controller ***********");
        return response;
    }


    @RequestMapping(value = "/services/rest/instance/{container}/{userName}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    public Response getStorageVolumes(@QueryParam("restEndPoint") String restEndPoint,
                                      @PathParam("container") String container, @PathParam("userName") String userName,
                                      String cookie) {

        logger.info("******* Start of getStorageVolumes() in controller ***********");
        Response response = null;
        response = myservice.getStorageVolumes(restEndPoint, container, userName, cookie);
        logger.info("******* end of getStorageVolumes() in controller ***********");
        return response;
    }

    @RequestMapping(value = "/services/rest/myservices", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
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
