package com.example.employeemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String employeeId;

  private String firstName;

  private String lastName;

  private String email;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date dob;

  private double salary;

  @ElementCollection
  private List<String> phoneNumbers;
}
