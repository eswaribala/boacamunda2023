package com.boa.appointmentapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boa.appointmentapi.models.Appointment;
import com.boa.appointmentapi.services.AppointmentService;

@RestController

public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping("/appointments")
	public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment){
		if(appointmentService.addAppointment(appointment)!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(appointment);
		}
		else
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Data Error");
	}
	
	@GetMapping("/appointments")
	public List<Appointment> fetchAllApointments(){
		return appointmentService.getAllAppointments();
	}
	
}
