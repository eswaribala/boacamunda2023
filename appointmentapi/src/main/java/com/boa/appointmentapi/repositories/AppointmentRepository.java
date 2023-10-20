package com.boa.appointmentapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.appointmentapi.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

}
