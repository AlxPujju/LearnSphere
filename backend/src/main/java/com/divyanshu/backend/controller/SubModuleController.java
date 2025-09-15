package com.divyanshu.backend.controller;


import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.model.SubModule;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.AuthenticatedUserService;
import com.divyanshu.backend.service.LogService;
import com.divyanshu.backend.service.ReportService;
import com.divyanshu.backend.service.SubModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/v1/submodule")
public class SubModuleController {


    @Autowired
    private SubModuleService subModuleService;


    @Autowired
    private LogService logService;


    @Autowired
    private ReportService reportService;


    @Autowired
    private AuthenticatedUserService authenticatedUserService;




    @PostMapping("/add")
    private ResponseEntity<SubModule> addSubModule(@RequestBody SubModule subModule ) {
        return ResponseEntity.ok(subModuleService.addSubModule(subModule));
    }


    @PutMapping("/edit/{subModuleId}")
    private ResponseEntity<SubModule> editSubModule(@PathVariable int subModuleId ,@RequestBody SubModule subModule ) {
        User tempUser = authenticatedUserService.getAuthenticatedUser();




        Log log = new Log();
        log.setContent(" " + subModuleId);
        log.setAction("Edited Sub Module: ");
        log.setUser(tempUser);
        SubModule subModule1 = subModuleService.editSubModule(subModuleId,subModule);


        if(tempUser != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("SubModule Edited");
            report.setLog(log2);




            reportService.saveReport(report);
        }
        return ResponseEntity.ok(subModule1);
    }


    @DeleteMapping("/delete/{subModuleId}")
    private ResponseEntity<String> deleteSubModule(@PathVariable int subModuleId) {
        User tempUser = authenticatedUserService.getAuthenticatedUser();


        Log log = new Log();
        log.setContent(" " + subModuleId);
        log.setAction("Deleted Sub Module: ");
        log.setUser(tempUser);


        boolean subMod  = subModuleService.deleteSubModule(subModuleId);
        if(tempUser != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("Document Deleted");
            report.setLog(log2);




            reportService.saveReport(report);
        }
        return ResponseEntity.ok("Delete Succeed : " + subMod);
    }


    @GetMapping("/list")
    private ResponseEntity<List<SubModule>> subModules() {
        return ResponseEntity.ok(subModuleService.getSubModules());
    }


    @GetMapping("/{subModuleId}")
    private ResponseEntity<SubModule> subModule(@PathVariable int subModuleId) {
        return ResponseEntity.ok(subModuleService.getSubModule(subModuleId));
    }


    @PostMapping("/assign/{subModuleId}/module/{moduleId}")
    private ResponseEntity<SubModule> assignSubModule(@PathVariable int subModuleId , @PathVariable int moduleId ) {
        return ResponseEntity.ok(subModuleService.assignSubModuleToModule(subModuleId,moduleId));
    }
//
//    @PostMapping("/chagneStatus/{subModuleId}")
//    private  ResponseEntity<> changeStatus(@PathVariable int sub){
//
//    }
}
