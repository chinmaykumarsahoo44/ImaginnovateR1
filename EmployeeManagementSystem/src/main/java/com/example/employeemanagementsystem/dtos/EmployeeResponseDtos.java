package com.example.employeemanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDtos {
  private String employeeId;
  private String firstName;
  private String lastName;
  private double salary;
  private double taxAmount;
  private double cessAmount;

}
