package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.dto.EmployeeDto;
import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.repository.EmployeeRepository;
import com.example.EmployeeManagementSystem.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.metamodel.Type;
import javax.transaction.Transactional;
import java.util.List;

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
    public String updateEmployee(EmployeeDto employeeDto){
        if(employeeRepository.existsById(employeeDto.getEmpID())){
            employeeRepository.save(modelMapper.map(employeeDto,Employee.class));
            return VarList.RSP_SUCCESS;
        }
        else{
          return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<EmployeeDto> getAllEmployees(){
       List<Employee> employeeList = employeeRepository.findAll();
       return  modelMapper.map(employeeList, new TypeToken<List<EmployeeDto>>(){}.getType());


    }
    public EmployeeDto searchEmployee(int empID){
        //check firs if the employee in the table using empId
        if(employeeRepository.existsById(empID)){
            Employee employee = employeeRepository.findById(empID).orElse(null); //get the employee from Employee entity using the empId
            return  modelMapper.map(employee,EmployeeDto.class); //then return the employee in the EmployeeDTo
        }
        else{
            return null;
        }
    }

}
