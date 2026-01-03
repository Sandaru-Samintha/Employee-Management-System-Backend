package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.dto.EmployeeDto;
import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.repository.EmployeeRepository;
import com.example.EmployeeManagementSystem.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public String saveEmployee(EmployeeDto employeeDto){

        //if the earlier add the employee in the database it will check this
        if(employeeRepository.existsById(employeeDto.getEmpID())){
            return  VarList.RSP_DUPLICATED;
        }
        else{
            employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }

}
