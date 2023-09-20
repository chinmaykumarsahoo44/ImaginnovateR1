package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dtos.EmployeeResponseDtos;
import com.example.employeemanagementsystem.entities.Employee;
import com.example.employeemanagementsystem.exceptions.FieldValidation;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.utils.Utils;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private Utils utils;

  public String saveEmployee(Employee employee) throws FieldValidation {

    // field validation
     if(employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()){
       throw new FieldValidation("Employee ID is required...");
     }
    if(employee.getFirstName() == null || employee.getFirstName().isEmpty()){
      throw new FieldValidation("First Name is required...");
    }
    if(employee.getLastName() == null || employee.getLastName().isEmpty()){
      throw new FieldValidation("LastName is required...");
    }
    if(employee.getSalary() == 0){
      throw new FieldValidation("Salary is required...");
    }
    if(employee.getDob() == null || employee.toString().isEmpty()){
      throw new FieldValidation("DOB is required...");
    }
    if(employee.getEmail() == null || employee.getEmail().isEmpty()){
      throw new FieldValidation("Email is required...");
    }
    if(employee.getPhoneNumbers() == null || employee.getPhoneNumbers().size()==0){
      throw new FieldValidation("PhoneNumber is required...");
    }
    // details saving into h2 data base
    employeeRepository.save(employee);
    return "Employee details saved successfully....";
  }

  public List<Employee> getAllEmployee() {
    // get all employee details
    return employeeRepository.findAll();
  }

  public List<EmployeeResponseDtos> getTaxDeductionDetailsForEmp() {
    List<Employee> allEmp = employeeRepository.findAll();
    List<EmployeeResponseDtos> listEmployeeResponseDtos = new ArrayList<>();
    for(Employee e: allEmp){
      EmployeeResponseDtos employeeResponseDtos  =new EmployeeResponseDtos();
      employeeResponseDtos.setFirstName(e.getFirstName());
      employeeResponseDtos.setLastName(e.getLastName());
      employeeResponseDtos.setEmployeeId(e.getEmployeeId());
      double salary   = utils.getYearlySalary(e.getSalary());
      employeeResponseDtos.setSalary(salary);

      LocalDate tempDob = e.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      //4--> April month
      int month  =  tempDob.getMonth().getValue();
      // net month to calculate net salary may be employee
      // has been join in mid of years so tax will be calculated on net salary only for 6 months
      int netMonth = 0;
      if(month > 4){
        netMonth =  12 - month;
      }
      double netSalary  =  e.getSalary()* netMonth;

      // percentage tax calculation
      int per =0;
      if(salary <=250000 ){
        per = 0;
      }
      else if(salary >250000 && salary <=500000){
        per = 5;
      }
      else if(salary >500000 && salary <=1000000){
        per = 10;
      }
      else if(salary >1000000){
        per = 20;
      }
      int cess =0;
      if( salary > 2500000){
        per+=2;
        cess = 2;
      }
      // income tax and cess tax calculation
      double incomeTax = netSalary * per/100;
      double cessAmount = 0;
      if(cess>0){
        cessAmount = netSalary * cess/100;
      }
      employeeResponseDtos.setTaxAmount(incomeTax);
      employeeResponseDtos.setCessAmount(cessAmount);
      listEmployeeResponseDtos.add(employeeResponseDtos);
    }
    return listEmployeeResponseDtos;
  }
}
