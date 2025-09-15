package com.divyanshu.backend.controller;

import com.divyanshu.backend.dto.DocumentDto;
import com.divyanshu.backend.model.Document;
import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.AuthenticatedUserService;
import com.divyanshu.backend.service.DocumentService;
import com.divyanshu.backend.service.LogService;
import com.divyanshu.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@RestController
@RequestMapping("/api/v1/document")
public class DocManagementController {


    @Autowired
    private DocumentService documentService;


    @Autowired
    private LogService logService;


    @Autowired
    private ReportService reportService;


    @Autowired
    private AuthenticatedUserService authenticatedUserService;


    @PostMapping("/add/{subModuleId}")
    private ResponseEntity<DocumentDto> addDocument(@RequestParam("file") MultipartFile file, @PathVariable int subModuleId) throws  Exception{
        return ResponseEntity.ok(documentService.addDocumentFile(file,subModuleId));
    }


    @PutMapping("/edit/{documentId}")
    private ResponseEntity<Document> editDocument(@PathVariable int documentId, @RequestParam("file") MultipartFile file ) throws Exception{
        User tempUser = authenticatedUserService.getAuthenticatedUser();


        Log log = new Log();
        log.setContent(" "+ documentId);
        log.setAction("Document updated:");
        log.setUser(tempUser);


        Document document =  documentService.editDocument(documentId,file);


        if(tempUser != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("Document Edited");
            report.setLog(log2);




            reportService.saveReport(report);
        }
        return ResponseEntity.ok(document);
    }


    @GetMapping("/list")
    private ResponseEntity<List<Document>> documents() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }


    @GetMapping("/{documentId}")
    private ResponseEntity<?> document(@PathVariable int documentId) {
        return ResponseEntity.ok(documentService.getDocument(documentId));
    }


    @GetMapping("/download/{documentId}")
    private ResponseEntity<?> downloadDocument(@PathVariable int documentId) throws Exception{
        return ResponseEntity.ok(documentService.downloadDoc(documentId));
    }
}


