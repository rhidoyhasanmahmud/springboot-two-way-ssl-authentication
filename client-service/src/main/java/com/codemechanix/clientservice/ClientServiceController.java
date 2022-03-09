package com.codemechanix.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping(value = "/client-service")
public class ClientServiceController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment environment;

    @RequestMapping(value = "/get-server-service-response", method = RequestMethod.GET)
    public String getResponseFromServerService() {
        System.out.println("In Client Service.");
        try {
            String msEndpoint = environment.getProperty("endpoint.server-service");
            System.out.println("Server Service Endpoint name : [" + msEndpoint + "]");
            assert msEndpoint != null;
            return restTemplate.getForObject(new URI(msEndpoint), String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Exception Occurred.";
    }
}
