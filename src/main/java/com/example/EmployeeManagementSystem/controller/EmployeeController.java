package com.example.EmployeeManagementSystem.controller;


import com.example.EmployeeManagementSystem.dto.EmployeeDto;
import com.example.EmployeeManagementSystem.dto.ResponseDto;
import com.example.EmployeeManagementSystem.service.EmployeeService;
import com.example.EmployeeManagementSystem.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResponseDto responseDto;


    @PostMapping("/saveEmployee")
    //when we use the ResponseEntity we can the pass the type of rest api /HTTP types
    public ResponseEntity saveEmployee(@RequestBody EmployeeDto employeeDto){
        try{

            //get the response from employee service is it success response or duplicate response
            String res = employeeService.saveEmployee(employeeDto);
            if(res.equals("00")){
                //this response pattern com from when we create the responseDot's pattern
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Success");
                responseDto.setContent(employeeDto); //this set the employee content as  object
                                                    //Frontend එකෙන් එවපු employee details  ටිකම ආපහු යවනව response  එක විදිහට
                return new ResponseEntity(responseDto,HttpStatus.ACCEPTED); // this return/pass the responseDto body of code,message,content and http status of accepted in to the frontend
                                                                            //Frontend එකට responseDto එකේ හදාගත්ත format  එකට අනුව response  එකක් යවනව
            } else if (res.equals("06")) {
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("Employee Registered");
                responseDto.setContent(employeeDto);
                return new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
            }else { //if it is not saved
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Error");
                responseDto.setContent(null); //this set the employee content as  object
                                              //Frontend එකෙන් එවපු employee details  ටික නැතුව ඒ වෙනුවට  ආපහු response  එක විදිහට null value  එකක් යවනවා
                return new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex){
            responseDto.setCode(VarList.RSP_FAIL);
            responseDto.setMessage(ex.getMessage());//Catch block එකෙන් අල්ල ගන්න exception එකම message එක විදිහට දෙනවා
            responseDto.setContent(null);
            return new ResponseEntity(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDto employeeDto){
        try {
            String res = employeeService.updateEmployee(employeeDto);
            if(res.equals("00")) {
               responseDto.setCode(VarList.RSP_SUCCESS);
               responseDto.setMessage("Update The Employee");
               responseDto.setContent(employeeDto);
               return new ResponseEntity(responseDto,HttpStatus.ACCEPTED);
           } else if (res.equals("01")) {
                responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDto.setMessage("Not A Registered Employee");
                responseDto.setContent(employeeDto);
                return new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
            }
            else {
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDto.setCode(VarList.RSP_FAIL);
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity getAllEmployees(){
        try {
            List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Success");
            responseDto.setContent(employeeDtoList);
            return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception ex){
            responseDto.setCode(VarList.RSP_FAIL);
            responseDto.setMessage(ex.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
