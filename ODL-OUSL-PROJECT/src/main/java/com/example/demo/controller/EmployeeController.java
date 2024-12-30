package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    
    @GetMapping ("/")
    public  String home(Model model) {
        List<Employee> list = service.getEmployees();
        model.addAttribute("list", list);
        return "home";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        Employee employee = new Employee();
        String fileName = file.getOriginalFilename();
        employee.setProfilePicture(fileName);
        employee.setContent(file.getBytes());
        employee.setSize(file.getSize());
        service.createEmployee(employee);
        model.addAttribute("success", "File Upload Successfully");
        return "teacher" ;
    }

    @GetMapping("/downloadfile")
    public  void downloadFile(@Param("id") Long id, Model model, HttpServletResponse response) throws IOException {
        Optional<Employee> temp = service.findEmployeeById(id);
        if (temp != null) {
            Employee employee =temp.get();
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue ="attachment; filename=" + employee.getProfilePicture();
            response.setHeader(headerKey, headerValue);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(employee.getContent());
            outputStream.close();
        }

    }
    @GetMapping("/image")
    public  void showImage(@Param("id") Long id, HttpServletResponse response, Optional<Employee> employee) throws IOException {
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST , "Id must not be null");
        }
        employee =service.findEmployeeById(id);
        if(employee.isPresent()) {
            response.setContentType("image/jpeg,image/png,img/gif,image/jpg,image/pdf");
            response.getOutputStream().write(employee.get().getContent());
            response.getOutputStream().close();
        }
        else {
           response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found for id " + id);
        }

    }
    @GetMapping ("/teacher")
    public  String teacher(Model model) {
        List<Employee> list = service.getEmployees();
        model.addAttribute("list", list);
        return "teacher";
    }
    @GetMapping ("/subjects")
    public  String subjects(Model model) {
        List<Employee> list = service.getEmployees();
        model.addAttribute("list", list);
        return "subjects";
    }
    @GetMapping ("/student")
    public  String student(Model model) {
        List<Employee> list = service.getEmployees();
        model.addAttribute("list", list);
        return "student";
    }
}