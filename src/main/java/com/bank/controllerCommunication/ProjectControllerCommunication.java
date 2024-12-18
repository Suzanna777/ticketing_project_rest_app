package com.bank.controllerCommunication;

import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.ResponseWrapper;
import com.bank.serviceMethod.ProjectServiceMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/project")
public class ProjectControllerCommunication {

    private final ProjectServiceMethod projectServiceMethod;

    public ProjectControllerCommunication(ProjectServiceMethod projectServiceMethod) {
        this.projectServiceMethod = projectServiceMethod;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDatadto> projectDataList = projectServiceMethod.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDataList, HttpStatus.OK));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code){
        ProjectDatadto projectDTO = projectServiceMethod.getByProjectCode(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",projectDTO, HttpStatus.OK));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDatadto project){
        projectServiceMethod.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDatadto project){
        projectServiceMethod.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated", HttpStatus.OK));

    }

    @DeleteMapping("/{projectcode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String projectcode){
        projectServiceMethod.delete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully deleted", HttpStatus.OK));


    }

    @GetMapping("/manager/project-status")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        List<ProjectDatadto> projectDTOList = projectServiceMethod.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){
        projectServiceMethod.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully completed", HttpStatus.OK));

    }





}

