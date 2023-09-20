package com.example.employeemanagementsystem.api;

import com.example.employeemanagementsystem.dtos.EmployeeResponseDtos;
import com.example.employeemanagementsystem.entities.Employee;
import com.example.employeemanagementsystem.service.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  @PostMapping("/add")
  public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
    String response = "";
    try {
      response = employeeService.saveEmployee(employee);
    }
    catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/allEmp")
  public ResponseEntity<List<Employee>>  getAllEmp(){
    List<Employee> response = employeeService.getAllEmployee();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/taxdetails")
  public ResponseEntity<List<EmployeeResponseDtos>> getTaxDeductionDetailsForEmp() {
    List<EmployeeResponseDtos> response  =new ArrayList<>();
    try {
      response  =employeeService.getTaxDeductionDetailsForEmp();
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
