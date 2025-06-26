package com.example.ITCompany.controller;

import com.example.ITCompany.entity.Department;
import com.example.ITCompany.entity.Employee;
import com.example.ITCompany.entity.StaffingTable;
import com.example.ITCompany.repository.DepartmentRepository;
import com.example.ITCompany.repository.EmployeeRepository;
import com.example.ITCompany.repository.StaffingTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final StaffingTableRepository staffingTableRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, StaffingTableRepository staffingTableRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.staffingTableRepository = staffingTableRepository;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAllWithDetails());
        return "employees/employee";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        addDepartmentsAndPositions(model);
        return "employees/create";
    }
    private String generateTempPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void addDepartmentsAndPositions(Model model){
        model.addAttribute("allDepartments", departmentRepository.findAll());
        model.addAttribute("allPositions", staffingTableRepository.findAll());
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addDepartmentsAndPositions(model);
            return "employees/create";
        }
        if (employee.getDepartment() == null || employee.getDepartment().getDepartmentName() == null ||
                employee.getDepartment().getDepartmentName().isEmpty()) {
            model.addAttribute("errorMessage", "Необходимо выбрать отдел.");
            addDepartmentsAndPositions(model);
            return "employees/create";
        }
        if (employee.getPost() == null || employee.getPost().getPost() == null || employee.getPost().getPost().isEmpty()) {
            model.addAttribute("errorMessage", "Необходимо выбрать должность.");
            addDepartmentsAndPositions(model);
            return "employees/create";
        }

        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
                model.addAttribute("errorMessage", "Сотрудник с таким e-mail уже существует.");
                addDepartmentsAndPositions(model);
                return "employees/create";
        }
        String password = generateTempPassword();
        employee.setPassword(password);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee.get());
        addDepartmentsAndPositions(model);
        return "employees/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addDepartmentsAndPositions(model);
            return "employees/edit";
        }
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent() && !existingEmployee.get().getEmployeeId().equals(id)) {
            model.addAttribute("errorMessage", "Сотрудник с таким e-mail уже существует.");
            addDepartmentsAndPositions(model);
            return "employees/edit";
        }
        Department department = departmentRepository.findById(employee.getDepartment().getDepartmentName())
                .orElseThrow(() -> new IllegalArgumentException("Неверный отдел"));
        employee.setDepartment(department);
        StaffingTable post = staffingTableRepository.findById(employee.getPost().getPost())
                .orElseThrow(() -> new IllegalArgumentException("Неверная должность"));
        employee.setPost(post);
        employee.setEmployeeId(id);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }
}