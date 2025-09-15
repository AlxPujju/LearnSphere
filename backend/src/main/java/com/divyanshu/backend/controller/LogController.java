package com.divyanshu.backend.controller;


import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
@RequestMapping("/api/v1/log")
public class LogController {


    @Autowired
    private LogService logService;


    @GetMapping("/list")
    public ResponseEntity<Page<Log>> logs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Log> logPage = logService.getAllLogs(pageable);
        return ResponseEntity.ok(logPage);
    }


    @GetMapping("/log/{logId}")
    public ResponseEntity<?> log(@PathVariable int logId) {
        Optional<Log> log = logService.getLogById(logId);
        return log.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{logId}")
    public ResponseEntity<?> delete(@PathVariable int logId) {
        logService.deleteLogById(logId);
        return ResponseEntity.ok().build();
    }


}
