package co.edu.uptc.employee_data_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private UUID id;

    @Column(name = "employee_code", nullable = false, unique = true, length = 20)
    private String employeeCode;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
        active = true;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
