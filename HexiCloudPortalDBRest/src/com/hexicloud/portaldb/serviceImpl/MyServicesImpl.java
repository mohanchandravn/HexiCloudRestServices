package com.hexicloud.portaldb.serviceImpl;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Array;

import java.math.BigInteger;

import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.json.JSONArray;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyServicesImpl {

    public String getCookie(@QueryParam("restEndPoint") String restEndPoint, @QueryParam("input") String input) {
        //Properties prop = readProperties();
        //String restEndPoint = (String) prop.get("endPoint");
        Response response = null;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(restEndPoint + "/authenticate/");
            Invocation.Builder invocationBuilder =
                target.request(MediaType.valueOf("application/oracle-compute-v3+json"));
            //  String input = "{\"user\":\"/Compute-a23605/vignesh.babu@oracle.com\",\"password\":\"Lnvb3456\"}";
            response = invocationBuilder.post(Entity.entity(input, MediaType.TEXT_PLAIN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null)
            return response.getCookies().get("nimbula").toString();
        else
            return "Empty Response";
    }
    
    public Response getShapes(@QueryParam("restEndPoint") String restEndPoint, String cookie) {
        // Properties prop = readProperties();
        // String restEndPoint = (String) prop.get("endPoint");

        Response response = null;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(restEndPoint + "/shape/");
        Invocation.Builder invocationBuilder = target.request(MediaType.valueOf("application/oracle-compute-v3+json"));
        //response = invocationBuilder.get();
        response = invocationBuilder.cookie(new Cookie("nimbula", cookie)).get();
        return response;
    }
    
    public Response getInstances(@QueryParam("restEndPoint") String restEndPoint,
                                 @PathParam("container") String container, @PathParam("userName") String userName,
                                 String cookie) {
        // Properties prop = readProperties();

        //String restEndPoint = (String) prop.get("endPoint");

        Response response = null;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(restEndPoint + "/instance/" + container + "/" + userName + "/");
        Invocation.Builder invocationBuilder = target.request(MediaType.valueOf("application/oracle-compute-v3+json"));
        //response = invocationBuilder.get();
        response = invocationBuilder.cookie(new Cookie("nimbula", cookie)).get();
        return response;
    }
    
    public Response getStorageVolumes(@QueryParam("restEndPoint") String restEndPoint,@PathParam("container") String container, @PathParam("userName") String userName,
                                      String cookie) {
       // Properties prop = readProperties();
        //String restEndPoint = (String) prop.get("endPoint");

        Response response = null;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(restEndPoint + "/storage/volume/" + container + "/" + userName + "/");
        Invocation.Builder invocationBuilder = target.request(MediaType.valueOf("application/oracle-compute-v3+json"));
        //response = invocationBuilder.get();
        response = invocationBuilder.cookie(new Cookie("nimbula", cookie)).get();
        return response;
    }
    
    public JSONObject getMyServices(@QueryParam("restEndPoint") String restEndPoint,
                                    @QueryParam("container") String container, @QueryParam("userName") String userName,
                                    @QueryParam("password") String password) {

        String idcontainer = "Compute-" + container;
        String requestInput = "{\"user\":\"/" + idcontainer + "/" + userName + "\",\"password\":\"" + password + "\"}";

        //{"user":"/Compute-a23605/vignesh.babu@oracle.com","password":"Lnvb3456"}

        // get cookie
        String cookieResponse = getCookie(restEndPoint, requestInput);
        String cookie = cookieResponse.substring(cookieResponse.indexOf("=") + 1, cookieResponse.indexOf(";"));

        //get shape details
        Response shapeResponse = getShapes(restEndPoint, cookie);
        //System.out.println(shapeResponse.readEntity(String.class));
        // get JSON object
        JSONParser parser = new JSONParser();
        BigInteger converter = new BigInteger("1073741824");

        // get instance details
        Response instanceResponse = getInstances(restEndPoint, idcontainer, userName, cookie);

        //getStorageVolumes
        Response storageResponse = getStorageVolumes(restEndPoint,idcontainer, userName, cookie);
        JSONObject finalResponse = new JSONObject();
        JSONArray finalJsonArray = new JSONArray();
        if (shapeResponse != null && instanceResponse != null && storageResponse != null) {
            try {
                String shapeStr = shapeResponse.readEntity(String.class);
                String instanceStr = instanceResponse.readEntity(String.class);
                String storageStr = storageResponse.readEntity(String.class);


                JSONObject shapeJSON = (JSONObject) parser.parse(shapeStr);
                JSONObject instanceJSON = (JSONObject) parser.parse(instanceStr);
                JSONObject storageJSON = (JSONObject) parser.parse(storageStr);


                JSONArray shapeItems = (JSONArray) shapeJSON.get("result");
                JSONArray instanceItems = (JSONArray) instanceJSON.get("result");
                JSONArray storageItems = (JSONArray) storageJSON.get("result");


                // Iterate shape items

                if (shapeItems != null) {
                    for (Object shpobject : shapeItems) {
                        JSONObject shpJSON = (JSONObject) shpobject;

                        // Iterate instances
                        if (instanceItems != null) {
                            for (Object insObject : instanceItems) {
                                JSONObject insJSON = (JSONObject) insObject;
                                int instanceCounter = 0;
                                Double ramSize = 0.0;
                                BigInteger totalVolume = new BigInteger("0");
                                if (shpJSON.get("name").equals(insJSON.get("shape"))) {

                                    // Iterate storage attachments array
                                    //System.out.println(insJSON.toJSONString());
                                    JSONArray storageAttachments = (JSONArray) insJSON.get("storage_attachments");
                                    
                                    if (storageAttachments != null && storageItems !=null) {
                                       // System.out.println("storageAttachments size" + storageAttachments.size() + "---storageItems size "+ storageItems.size());
                                        for (int i = 0; i < storageAttachments.size(); i++) {
                                            JSONObject attchJSON = (JSONObject) storageAttachments.toArray()[i];
                                          
                                            // Iterate storage volumes

                                            for (int j = 0; j < storageItems.size(); j++) {
                                                JSONObject storJSON = (JSONObject) storageItems.toArray()[j];
                                                
                                                System.out.println("Attachments volume name : " + attchJSON.get("storage_volume_name") + "----Storage items volume name : " + storJSON.get("name") + "--size---" + storJSON.get("size").toString());
                                                
                                                // This condition is to be removed if storage name is set properly in GSE
                                                if(attchJSON.get("storage_volume_name").toString().equals("Compute-gse00000514/cloud.admin/bitnami-nodejs-da10"))
                                                {
                                                    totalVolume = new BigInteger(storJSON.get("size").toString());
                                                }
                                                
                                                if (attchJSON.get("storage_volume_name").toString().equals(storJSON.get("name").toString())) {
                                                    System.out.println("Volume names equal-- volume Name is :" + storJSON.get("name"));
                                                    BigInteger instanceVolume = new BigInteger(storJSON.get("size").toString());
                                                    totalVolume = totalVolume.add(instanceVolume);
                                                }

                                            }

                                            ramSize = Double.parseDouble(shpJSON.get("ram").toString()) / (1024);
                                        }
                                    }

                                    System.out.println("totalVolume" + totalVolume.divide(converter));
                                    System.out.println("ramSize" + ramSize);
                                    JSONObject finalJsonObj = new JSONObject();
                                    finalJsonObj.put("serviceType", "Compute");
                                    finalJsonObj.put("runningInstances", "1");
                                    finalJsonObj.put("cpuUsage", totalVolume.divide(converter));
                                    finalJsonObj.put("ramSize", ramSize);
                                    finalJsonObj.put("usageMetric", "GB");
                                    finalJsonObj.put("status", insJSON.get("state"));
                                    finalJsonArray.add(finalJsonObj);
                                }
                            }
                        }
                    }
                }
                System.out.println("Final JSON" + finalJsonArray.toJSONString());
                finalResponse.put("result", finalJsonArray);


            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        //return storageResponse;
        return finalResponse;
    }
    
}
