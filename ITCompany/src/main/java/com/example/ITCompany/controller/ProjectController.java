package com.example.ITCompany.controller;

import com.example.ITCompany.entity.Participation;
import com.example.ITCompany.entity.ParticipationId;
import com.example.ITCompany.entity.Project;
import com.example.ITCompany.repository.EmployeeRepository;
import com.example.ITCompany.repository.ParticipationRepository;
import com.example.ITCompany.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "projects/projects";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("allEmployees", employeeRepository.findAllWithDepartments());
        return "projects/create";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute("project") Project project,
                              BindingResult result, @RequestParam(name = "employeeIds", required = false)
                                  List<Integer> employeeIds, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {System.out.println("Ошибка валидации: " + error.toString());
            });
            return "projects/create";
        }
        boolean projectExists = projectRepository.existsByProjectNameAndStartProjectAndEndProject(
                project.getProjectName(),
                project.getStartProject(),
                project.getEndProject());
        if (projectExists) {
            model.addAttribute("errorMessage", "Проект с такими параметрами уже существует");
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "projects/create";
        }
        try {
            Project savedProject = projectRepository.save(project);
            if (employeeIds != null) {
                for (Integer employeeId : employeeIds) {
                    Participation participation = new Participation();
                    ParticipationId id = new ParticipationId();
                    id.setEmployeeId(employeeId);
                    id.setProjectId(savedProject.getProjectId());
                    participation.setId(id);
                    participation.setEmployee(employeeRepository.findById(employeeId).orElseThrow());
                    participation.setProject(savedProject);
                    participationRepository.save(participation);
                }
            }
            return "redirect:/projects";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Произошла ошибка при сохранении проекта");
            model.addAttribute("allEmployees", employeeRepository.findAll());
            return "projects/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        return projectRepository.findById(id)
                .map(project -> {
                    model.addAttribute("project", project);
                    model.addAttribute("allEmployees", employeeRepository.findAllWithDepartments());
                    model.addAttribute("assignedEmployeeIds", participationRepository.findEmployeeIdsByProjectId(id));
                    return "projects/edit";
                }).orElse("redirect:/projects");
    }

    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable Integer id,
                                @ModelAttribute("project") Project project,
                                BindingResult result,
                                @RequestParam(name = "employeeIds", required = false) List<Integer> employeeIds, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("allEmployees", employeeRepository.findAllWithDepartments());
            model.addAttribute("assignedEmployeeIds", participationRepository.findEmployeeIdsByProjectId(id));
            return "projects/edit";
        }
        if (projectRepository.existsByProjectNameAndDatesAndIdNot(
                project.getProjectName(),
                project.getStartProject(),
                project.getEndProject(),
                id)) {
            model.addAttribute("errorMessage", "Проект с такими параметрами уже существует");
            model.addAttribute("allEmployees", employeeRepository.findAllWithDepartments());
            model.addAttribute("assignedEmployeeIds", participationRepository.findEmployeeIdsByProjectId(id));
            return "projects/edit";
        }
        try {
            project.setProjectId(id);
            Project updatedProject = projectRepository.save(project);
            return "redirect:/projects";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при обновлении проекта: " + e.getMessage());
            model.addAttribute("allEmployees", employeeRepository.findAllWithDepartments());
            model.addAttribute("assignedEmployeeIds", participationRepository.findEmployeeIdsByProjectId(id));
            return "projects/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Integer id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
}