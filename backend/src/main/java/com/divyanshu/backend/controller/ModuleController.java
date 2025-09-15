package com.divyanshu.backend.controller;


import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.model.Module;
import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.AuthenticatedUserService;
import com.divyanshu.backend.service.LogService;
import com.divyanshu.backend.service.ModuleService;
import com.divyanshu.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequestMapping("/api/v1/module")
@RestController
public class ModuleController {


    @Autowired
    private  LogService logService;


    @Autowired
    private  ModuleService moduleService;


    @Autowired
    private ReportService reportService;


    @Autowired
    private AuthenticatedUserService authenticatedUserService;


    @PostMapping("/add")
    public ResponseEntity<Module> addModule(@RequestBody Module module ){
        return  ResponseEntity.ok(moduleService.addModule(module));
    }
    @PutMapping("/edit/{moduleId}")
    public ResponseEntity<Module> editModule(@RequestBody Module module ,@PathVariable int moduleId){
        User tempUser = authenticatedUserService.getAuthenticatedUser();


        Log log = new Log();
        log.setContent(module.toString());
        log.setAction("Module Edited :");
        log.setUser(tempUser);
        Module tempModule =  moduleService.editModule(moduleId , module);
        if(tempUser != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("Module Edited");
            report.setLog(log2);




            reportService.saveReport(report);
        }
        return  ResponseEntity.ok(tempModule);
    }
    @DeleteMapping("/delete/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable int moduleId){
        boolean res = moduleService.deleteModule(moduleId);
        return  ResponseEntity.ok("Module deleted" + res);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<Module>> modules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(moduleService.getModules(page, size, sortBy));
    }
    @GetMapping("/{moduleId}")
    public ResponseEntity<Module> module(@RequestBody int moduleId){
        return  ResponseEntity.ok(moduleService.getModule(moduleId));
    }
    @PostMapping("/assign/{moduleId}/course/{courseId}")
    public ResponseEntity<Module> assignModules(@PathVariable int moduleId , @PathVariable int courseId){
        return  ResponseEntity.ok(moduleService.assignModuleToCourse(moduleId,courseId));
    }
}
