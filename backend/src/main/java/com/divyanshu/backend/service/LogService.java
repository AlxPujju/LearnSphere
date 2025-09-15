package com.divyanshu.backend.service;


import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;


    public Page<Log> getAllLogs(Pageable pageable) {
        return logRepository.findAll(pageable);
    }


    public Optional<Log> getLogById(int id) {
        return logRepository.findById(id);
    }


    public Log saveLog(Log log) {
        return logRepository.save(log);


    }


    public void deleteLogById(int id) {
        logRepository.deleteById(id);
    }


}
