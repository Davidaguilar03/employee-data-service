package co.edu.uptc.employee_data_service.services;

import co.edu.uptc.employee_data_service.models.Employee;
import co.edu.uptc.employee_data_service.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<Employee> getEmployees(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(1, Math.min(size, 100));
        Pageable pageable = PageRequest.of(
                safePage,
                safeSize,
                Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id"))
        );
        return employeeRepository.findAll(pageable);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        validateEmailUniqueness(employee.getEmail());
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().isBlank()) {
            employee.setEmployeeCode(generateEmployeeCode());
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        Employee entity = findByIdOrThrow(id);
        employeeRepository.delete(entity);
    }

    private Employee findByIdOrThrow(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id " + id));
    }

    private void validateEmailUniqueness(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    private String generateEmployeeCode() {
        long next = employeeRepository.count() + 1;
        String code = "E" + next;
        while (employeeRepository.existsByEmployeeCode(code)) {
            next++;
            code = "E" + next;
        }
        return code;
    }
}
