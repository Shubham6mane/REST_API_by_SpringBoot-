package netjavaguides.springboot.controller;

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

import netjavaguides.springboot.exception.ResourceNotFoundException;
import netjavaguides.springboot.model.Employee;
import netjavaguides.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	@CrossOrigin(origins ="http://localhost:4200")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//create employee rest api
	@PostMapping("/employees")
	@CrossOrigin(origins="http://localhost:4200")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:"+ id));
		return ResponseEntity.ok(employee);
		}
	
	
	@PutMapping("/employees/{id}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:"+id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:"+id));
		employeeRepository.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}



