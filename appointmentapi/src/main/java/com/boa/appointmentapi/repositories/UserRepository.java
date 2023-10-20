package com.boa.appointmentapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.appointmentapi.models.User;


public interface UserRepository extends JpaRepository<User,String>{

}
