package com.example.ITCompany.controller;

import com.example.ITCompany.entity.Department;
import com.example.ITCompany.repository.DepartmentRepository;
import com.example.ITCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public String listDepartments(Model model) {
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(dept -> {
            long count = employeeRepository.countByDepartment(dept);
            dept.setNumberOfEmployees((int)count);
        });
        model.addAttribute("departments", departments);
        return "departments/departments";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("allEmployees", employeeRepository.findAll());
        return "departments/create";
    }

    @PostMapping("/save")
    public String saveDepartment( @ModelAttribute("department") Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "departments/create";
        }
        if (departmentRepository.existsById(department.getDepartmentName())) {
            model.addAttribute("errorMessage", "Отдел с таким названием уже существует");
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "departments/create";
        }
        if (department.getDepartmentLeaderName()  == null || department.getDepartmentLeaderName().isEmpty()) {
            model.addAttribute("errorMessage", "Необходимо выбрать руководителя.");
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "departments/create";
        }
        if (department.getDepartmentLeaderName() != null && !department.getDepartmentLeaderName().isEmpty()) {
            Department existingDept = departmentRepository.findByDepartmentLeaderName(department.getDepartmentLeaderName());
            if (existingDept != null) {
                model.addAttribute("errorMessage",
                        "Этот руководитель уже назначен в отдел: " + existingDept.getDepartmentName());
                model.addAttribute("allEmployees", employeeRepository.findAll());
                return "departments/create";
            }
        }
        department.setNumberOfEmployees(0);
        departmentRepository.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{departmentName}")
    public String showEditForm(@PathVariable String departmentName, Model model) {
        Department department = departmentRepository.findById(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("Неверное название отдела: " + departmentName));
        model.addAttribute("department", department);
        model.addAttribute("allEmployees", employeeRepository.findAll());
        return "departments/edit";
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute("department") Department department, BindingResult result, Model model) {
        if (department.getDepartmentLeaderName() != null && !department.getDepartmentLeaderName().isEmpty()) {
            Department existingDept = departmentRepository.findByDepartmentLeaderName(department.getDepartmentLeaderName());
            if (existingDept != null && !existingDept.getDepartmentName().equals(department.getDepartmentName())) {
                model.addAttribute("errorMessage",
                        "Этот руководитель уже назначен в отдел: " + existingDept.getDepartmentName());
                model.addAttribute("allEmployees", employeeRepository.findAll());
                return "departments/edit";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "departments/edit";
        }
        departmentRepository.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable String id) {
        departmentRepository.deleteById(id);
        return "redirect:/departments";
    }
}