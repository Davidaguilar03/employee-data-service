package co.edu.uptc.employee_data_service.repositories;

import co.edu.uptc.employee_data_service.models.Employee;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

}
