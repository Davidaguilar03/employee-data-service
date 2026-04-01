package co.edu.uptc.employee_data_service.controllers;

import co.edu.uptc.employee_data_service.models.Employee;
import co.edu.uptc.employee_data_service.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size
    ) {
        return employeeService.getEmployees(page, size);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee request) {
        Employee created = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
