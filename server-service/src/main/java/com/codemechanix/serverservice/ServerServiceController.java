package com.codemechanix.serverservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/server-service")
public class ServerServiceController {

    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public String getResponse() {
        try {
            System.out.println("In server-service.");
            return "Hello, I'm from Server-Service.";
        } catch (Exception exception) {
            exception.fillInStackTrace();
            return "Hello, I'm from Server-Service. But Exception Occur.";
        }
    }
}
