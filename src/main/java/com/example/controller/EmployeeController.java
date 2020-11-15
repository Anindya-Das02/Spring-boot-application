package com.example.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.models.Employee;
import com.example.repository.EmployeeRepository;

@RestController
@RequestMapping(value = "/rest/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping(path = "/")
	@ResponseBody
	public List<Employee> getEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping(path = "/add",consumes = {"application/json"})
	@ResponseBody
	public Map<String, Object> addEmployee(@RequestBody Employee employee) {
		Map<String, Object> insertStatus = new HashMap<>();
		try {
			employeeRepository.save(employee);
			insertStatus.put("inserted", Boolean.TRUE);
			insertStatus.put("message", "successfully inserted employee record");
		}catch (Exception e) {
			insertStatus.put("inserted", Boolean.FALSE);
			insertStatus.put("message", e.getMessage());
		}
		return insertStatus;
	}
	
	@GetMapping(path = "/{empid}", produces = {"application/json"})
	@ResponseBody
	public Object getEmployeeById(@PathVariable("empid") int empid) {
		Employee employee = employeeRepository.findById(empid).orElse(null);
		if(employee == null) {
			HashMap<String,String> msg = new HashMap<>();
			msg.put("for_id", String.valueOf(empid));
			msg.put("message", "No such Employee Exists" );
			return msg;
		}
		return employee;
	}
	
	@PutMapping(path = "/update/{empid}", consumes = {"application/json"})
	@ResponseBody
	public Object updateEmployeeRecord(@PathVariable("empid") int empid,@RequestBody Employee requestEmployee){
		Employee employee = employeeRepository.findById(empid).orElse(null);
		if(employee == null) {
			HashMap<String,String> msg = new HashMap<>();
			msg.put("for_id", String.valueOf(empid));
			msg.put("message", "No such Employee Exists" );
			return msg;
		}
		employee.setName(requestEmployee.getName());
		employee.setSalary(requestEmployee.getSalary());
		employeeRepository.save(employee);
		return employee;
	}
	
	@DeleteMapping(path = "/delete/{empid}", consumes = {"application/json"})
	@ResponseBody
	public Map<String, Boolean> deleteEmployeeRecord(@PathVariable("empid") int empid){
		Employee employee = employeeRepository.findById(empid).orElse(null);
		Map<String, Boolean> deleteStatus = new HashMap<>(); 
		employeeRepository.delete(employee);
		deleteStatus.put("deleted", Boolean.TRUE);
		return deleteStatus;		
	}

}