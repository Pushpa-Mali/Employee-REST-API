package com.pushpa.EmployeeManagement.controller;

import com.pushpa.EmployeeManagement.exception.ResourceNotFoundException;
import com.pushpa.EmployeeManagement.model.Employee;
import com.pushpa.EmployeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository empRepo;

    @GetMapping("/employess")
    public List<Employee> getAll(){
        return empRepo.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id)
    {
        Employee employee = empRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :" + id));
           return ResponseEntity.ok(employee);
    }

    @PostMapping("/employess")
    public Employee createEmployee(@RequestBody Employee emp){
        return empRepo.save(emp);

    }
    @PutMapping("/employees/{id}")
    public ResponseEntity < Employee > updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = empRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFname(employeeDetails.getFname());
        employee.setLname(employeeDetails.getLname());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = empRepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity <Map< String, Boolean >> deleteEmployee(@PathVariable Long id) {
        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        empRepo.delete(employee);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
