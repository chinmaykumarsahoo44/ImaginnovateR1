package com.example.employeemanagementsystem.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

  public double getYearlySalary(double salary){
    return salary * 12;
  }

}
