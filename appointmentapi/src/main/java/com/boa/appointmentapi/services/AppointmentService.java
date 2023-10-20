package com.boa.appointmentapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boa.appointmentapi.models.Appointment;
import com.boa.appointmentapi.repositories.AppointmentRepository;

@Service
public class AppointmentService {
    @Autowired
	private AppointmentRepository appointmentRepository;
    
    //save
    public Appointment addAppointment(Appointment appointment) {
    	return appointmentRepository.save(appointment);
    }
    
    //retrieval
    public List<Appointment> getAllAppointments() {
    	return appointmentRepository.findAll();
    }
}
