package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import spring_mvc.enums.EmploymentType;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String surname;

    @Column
    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Column(unique = true)
    @Email(message = "Enter a valid email")
    private String email;

    @Column
    private BigDecimal salary;

    @Column
    private String position;

    @Column
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

}
