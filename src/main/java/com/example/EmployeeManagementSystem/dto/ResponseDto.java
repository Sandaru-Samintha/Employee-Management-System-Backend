package com.example.EmployeeManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
//this is creating for mage the response in the employee controller
public class ResponseDto {
    private String code;
    private String message;
    private Object content;
}
