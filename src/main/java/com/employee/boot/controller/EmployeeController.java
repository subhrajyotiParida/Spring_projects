package com.employee.boot.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.boot.exception.ResourceNotFoundException;
import com.employee.boot.model.Employee;
import com.employee.boot.repository.EmlployeeRepository;

@RestController
@RequestMapping ("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	private EmlployeeRepository empRepository;
	 
	
	/**
	 * Get all employee list
	 * */
	@GetMapping("/employess")
	public  List<Employee> getAllEmployee(){
		return empRepository.findAll();
	}
	
	/**
	 * Create employee 
	 * */
	
	@PostMapping("/employess")
	public  Employee createEmployee(@RequestBody Employee emp){
	     System.out.println("Service layer hit here "+emp.toString());
		return empRepository.save(emp);
	}
	
	
	/**
	 * Get employee by ID
	 * */
	@GetMapping("/employess/{id}")
	public  ResponseEntity<Employee> getEMployeeById(@PathVariable Long id){
	      Employee emp=this.empRepository.findById(id).orElseThrow(()->(new ResourceNotFoundException("Emplyee not found for id "+id)));
		return ResponseEntity.ok(emp);
	}
	/**
	 * Update employee
	 * */
	@PutMapping("/employess/{id}")
	public  ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee emplyeedDetails){
		 Employee emp=this.empRepository.findById(id).orElseThrow(()->(new ResourceNotFoundException("Emplyee not found for id "+id)));
		 emp.setFirstname(emplyeedDetails.getFirstname());
		 emp.setLastname(emplyeedDetails.getLastname());
		 emp.setEmail(emplyeedDetails.getEmail());
		 this.empRepository.save(emp);
		 return  ResponseEntity.ok(emp);
	}
	
	
	/**
	 *  Delete by ID
	 * */
	@DeleteMapping("/employess/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployeebyId(@PathVariable Long id){
		 Employee emp=this.empRepository.findById(id).orElseThrow(()->(new ResourceNotFoundException("Emplyee not found for id "+id)));
	     this.empRepository.delete(emp);
		Map<String,Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
