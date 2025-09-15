package com.divyanshu.backend.service;


import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;


    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }


    public Optional<Report> getReportById(int id) {
        return reportRepository.findById(id);
    }


    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public void deleteReportById(int id) {
        reportRepository.deleteById(id);
    }
}
