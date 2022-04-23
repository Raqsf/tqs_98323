package com.test_containers.test_containers;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="employeeName")
    private String name;

    @Column(name="phoneNumber")
	private int phoneNumber;

    @Column(name="dateOfBirth")
	private LocalDate dateOfBirth;

    public Employee() {}

    public Employee(String name, int phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    /* public void setName(String name) {
        this.name = name;
    } */
   
}