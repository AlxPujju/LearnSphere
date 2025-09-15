package com.divyanshu.backend.controller;


import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;


    @PostMapping("/add")
    public ResponseEntity<Report> addReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }


    @PutMapping("/edit/{reportId}")
    public ResponseEntity<Report> editReport(@PathVariable int reportId, @RequestBody Report report) {
        Optional<Report> existing = reportService.getReportById(reportId);
        if (existing.isPresent()) {
            report.setId(reportId);
            Report updated = reportService.saveReport(report);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable int reportId) {
        reportService.deleteReportById(reportId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/list")
    public ResponseEntity<List<Report>> listReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }


    @GetMapping("/{reportId}")
    public ResponseEntity<Report> getReport(@PathVariable int reportId) {
        Optional<Report> report = reportService.getReportById(reportId);
        return report.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
