package com.boa.appointmentapi.delegates;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.boa.appointmentapi.models.Appointment;
//import com.boa.appointmentapi.models.Appointment;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.wnameless.json.flattener.JsonFlattener;

//import net.minidev.json.JSONObject;
//import net.minidev.json.parser.JSONParser;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("receiveappointments")
public class ReceivaAllAppointmentsDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(ReceivaAllAppointmentsDelegate.class.getName());
  @Value("${url}")
  private String url;
  @Autowired
  private RestTemplate restTemplate;
  public void execute(DelegateExecution execution) throws Exception {
         
    
    //call the rest service
    
     HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON); 
 	
	HttpEntity request = new HttpEntity<String>(null,headers);
		 ResponseEntity<String> responseEntityStr = restTemplate.
       exchange(url+"/appointments",HttpMethod.GET, request,
		  String.class); 
		 System.out.println(responseEntityStr.getBody());
		 //Map<String, Object> data=parseString(responseEntityStr.getBody());
		 
		  ObjectMapper objectMapper = new ObjectMapper();
		 List<Appointment> appointmentList = Arrays.asList(objectMapper.readValue(responseEntityStr.getBody(), Appointment[].class));
		 appointmentList.stream()
		        .forEach((appointment) -> System.out.println(appointment.getAppointmentId() ));
		    System.out.println("List size is " +appointmentList.size());
		 execution.setVariable("appointments", appointmentList);

  }
  

  
}
